package ir.blog.blog.Controller;

import ir.blog.blog.DTO.PostDto;
import ir.blog.blog.Model.Post;
import ir.blog.blog.Model.Tag;
import ir.blog.blog.Model.User;
import ir.blog.blog.Service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("controller-post")
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
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> savepost(@PathVariable @Valid PostDto postDto, Authentication auth) {
        Post post = new Post();
        post.setTitle(postDto.title());
        post.setContent(postDto.content());
        post.setPostTag(postDto.PostTag());
        post.setCategory(postDto.category());
        post.setExcerpt(postDto.excerpt());
        post.setViews(0);
        User user = userService.findByUsername(auth.getName());
        post.setAuthor(user);
        postService.CreatePost(post);
        if (post.getPostslug() == null || post.getPostslug().isBlank()) {
            post.setPostslug(post.getTitle() + "-" + post.getPostId());
            post = postService.CreatePost(post);
        }
        return ResponseEntity.ok().body(200);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatepost(@PathVariable  int id,@RequestBody @Valid PostDto postDto) {
        Post post = postService.getbyId(id);
        post.setTitle(postDto.title());
        post.setContent(postDto.content());
        post.setPostTag(postDto.PostTag());
        post.setCategory(postDto.category());
        post.setExcerpt(postDto.excerpt());
        postService.CreatePost(post);
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
}
