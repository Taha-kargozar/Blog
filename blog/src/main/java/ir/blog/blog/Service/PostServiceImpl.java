package ir.blog.blog.Service;

import ir.blog.blog.DTO.PostDto;
import ir.blog.blog.Model.*;
import ir.blog.blog.Repository.PostRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ir.blog.blog.Model.Status.PUBLISHED;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final UserService userService;

    public PostServiceImpl(PostRepo postRepo, CategoryService categoryService, TagService tagService, UserService userService) {
        this.postRepo = postRepo;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.userService = userService;
    }


    @Override
   public Post CreatePost(Post post,Authentication auth) {
       post.setStatus(post.getStatus() != null ? post.getStatus() : Status.DRAFT);
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) auth.getPrincipal()).getUsername();
            User author = userService.findByUsername(username);
            post.setAuthor(author);
        }
       return postRepo.save(post);
   }
    @Override
    public Post UpdatePost(int id, Post post) {
        return postRepo.findById(id)
                .map(existing -> {
                    existing.setTitle(post.getTitle());
                    existing.setContent(post.getContent());
                    existing.setPostslug(post.getTitle());
                    existing.setStatus(post.getStatus());
                    return postRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }
    @Override
    public void DeletePost(int Id) {
            postRepo.deleteById(Id);
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
       return postRepo.findAll(pageable);
    }

    @Override
    public Page<Post> getAllPostsList(User user,Pageable pageable) {
        return postRepo.findAllByAuthor(user,pageable);
    }

    @Override
    public Page<Post> getbyName(String name, Pageable pageable) {
        return postRepo.findByTitle(name,pageable);
    }

    @Override
    public Page<Post> getbyNameAuthor(String name, Pageable pageable) {
        return postRepo.findByAuthorName(name,pageable);
    }

    @Override
    public Post getbyId(int id) {
        return postRepo.findById(id)
                .orElseThrow();
    }

    @Override
    public Optional<Post> findBySlug(String slug) {
        return postRepo.findByPostslug(slug);
    }

    @Override
    public Page<Post> findPublishedPosts(Pageable pageable) {
        return postRepo.findByStatus(PUBLISHED, pageable);
    }

    @Override
    public Optional<Post> findPublishedPostbyslug(String slug) {
        return postRepo.findByPostslugAndStatus(slug,PUBLISHED);
    }

    @Override
    public List<Post> findByTag(Tag tag) {
        return postRepo.findByPostTag(tag);
    }

   @Override
   public Post savePostFromDto(PostDto dto
         //  , Authentication auth
   ) {
       Post post = new Post();
       post.setTitle(dto.title());
       post.setContent(dto.content());
       post.setExcerpt(dto.excerpt());
       post.setStatus(dto.status() != null ? dto.status() : Status.DRAFT);

       // 👇 دریافت صحیح کاربر
       //String username = auth.getName();
       User user = userService.findByUsername("admin");
       if (user == null) {
           throw new IllegalArgumentException("کاربر پیدا نشد: " + "admin");
       }
       post.setAuthor(user);

       // دسته‌بندی
       if (dto.categoryID() != null) {
           Category category = categoryService.findById(dto.categoryID())
                   .orElseThrow(() -> new IllegalArgumentException("دسته‌بندی یافت نشد"));
           post.setCategory(category);
       }

       // تگ‌ها
       if (dto.PostTagID() != null && !dto.PostTagID().isEmpty()) {
           List<Tag> tags = dto.PostTagID().stream()
                   .map(id -> tagService.findById(id)
                           .orElseThrow(() -> new IllegalArgumentException("تگ یافت نشد: " + id)))
                   .collect(Collectors.toList());
           post.setPostTag(tags);
       } else {
           post.setPostTag(new ArrayList<>());
       }

       // slug
       String slug = dto.title().toLowerCase()
               .replaceAll("[^\\w\\s-]", "")
               .replaceAll("[-\\s]+", "-")
               .replaceAll("--+", "-");
       post.setPostslug(slug);

       post.setViews(0);

       return postRepo.save(post);
   }
}
