package ir.blog.blog.Controller;

import ir.blog.blog.DTO.PostDto;
import ir.blog.blog.Model.*;
import ir.blog.blog.Service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/controller-post")
public class PostController {
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final PostService postService;
    private final TagService tagService;
    private final UserService userService;

    public PostController(CategoryService categoryService, CommentService commentService, PostService postService, TagService tagService, UserService userService) {
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.postService = postService;
        this.tagService = tagService;
        this.userService = userService;
    }

    private void dtotoentity(PostDto dto,Post post
           , Authentication auth
    ) {
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setExcerpt(dto.excerpt());
        post.setStatus(dto.status());

        User user = userService.findByUsername(auth.getName());
        post.setAuthor(user);

        if (dto.categoryID() != null) {
            Category category = categoryService.findById(dto.categoryID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            post.setCategory(category);
        } else {
            post.setCategory(null);
        }

        if (dto.PostTagID() != null && !dto.PostTagID().isEmpty()) {
            List<Tag> tags = new ArrayList<>();
            for (Integer id : dto.PostTagID()) {
                Tag tag = tagService.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found: " + id));
                tags.add(tag);
            }
            post.setPostTag(tags);
        } else {
            post.setPostTag(new ArrayList<>());
        }
    }
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> savepost(@RequestBody @Valid PostDto postDto
           , Authentication auth
    )
    {
        Post post = new Post();
        dtotoentity(postDto,post, auth);
        if (post.getPostslug() == null || post.getPostslug().isBlank()) {
            post.setPostslug(post.getTitle() + "-" + post.getPostId());
            post = postService.CreatePost(post,auth);
        }
        return ResponseEntity.ok().body(200);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatepost(@PathVariable  int id,@RequestBody @Valid PostDto postDto
           , Authentication auth
    ) {
       Optional<Post> postOptional = Optional.ofNullable(postService.getbyId(id));
        Post post = postOptional.get();
       dtotoentity(postDto,post, auth);
       Post savedpost = postService.CreatePost(post,auth);
        return ResponseEntity.ok(postDto);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (postService.getbyId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        postService.DeletePost(id);
        return ResponseEntity.ok().body(null);
    }
    @GetMapping("/all")
    public ResponseEntity<Page<Post>> getAll(Pageable pageable) {
        Page<Post> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        postService.getbyId(id);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getBySlug(@PathVariable String slug) {
        postService.findBySlug(slug);
        return ResponseEntity.ok().body(slug);
    }
    @GetMapping("/posts/{slug}")
    public ResponseEntity<Optional<Post>> showPostDetail(@PathVariable String slug, Model model,Pageable pageable) {
            Optional<Post> post = postService.findPublishedPostbyslug(slug);


            List<Comment> approvedComments = commentService.findApprovedByPostId(post.get().getPostId());

            model.addAttribute("post", post);
            model.addAttribute("comments", approvedComments);
            model.addAttribute("newComment", new Comment());

            return ResponseEntity.ok().body(post);
    }


}
