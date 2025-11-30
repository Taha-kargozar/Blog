package ir.blog.blog.Service;

import ir.blog.blog.Model.Post;
import ir.blog.blog.Model.Status;
import ir.blog.blog.Model.Tag;
import ir.blog.blog.Model.User;
import ir.blog.blog.Repository.PostRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;

    public PostServiceImpl(PostRepo postRepo) {
        this.postRepo = postRepo;
    }


    @Override
   public Post CreatePost(Post post) {
       post.setStatus(post.getStatus() != null ? post.getStatus() : Status.DRAFT);
       post.setCreatedAt(LocalDateTime.now());
       if (Status.PUBLISHED.equals(post.getStatus())) {
           post.setPublishedAt(LocalDateTime.now());
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
                    existing.setUpdatedAt(LocalDateTime.now());
                    if (post.getStatus().PUBLISHED.equals(post.getStatus()) && existing.getPublishedAt() == null) {
                        existing.setPublishedAt(LocalDateTime.now());
                    }
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
    public Page<Post> getAllPostsList(User user) {
        return postRepo.findAllByAuthor(user);
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
        return postRepo.findByStatus(Status.PUBLISHED, pageable);
    }

    @Override
    public Page<Post> findPublishedPostsbyslug(String slug,Pageable pageable) {
        return postRepo.findByStatusaAndPostslug(Status.PUBLISHED,slug,pageable);
    }

    @Override
    public List<Post> findByTag(Tag tag) {
        return postRepo.findByPostTag(tag);
    }
}
