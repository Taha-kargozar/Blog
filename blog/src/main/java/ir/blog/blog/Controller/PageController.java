package ir.blog.blog.Controller;

import ir.blog.blog.DTO.PostDto;
import ir.blog.blog.Model.*;
import ir.blog.blog.Service.*;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class PageController {
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final CommentService commentService;
    private final PostController postController;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final UserService userService;

    public PageController(PasswordEncoder passwordEncoder, PostService postService, CommentService commentService, PostController postController, CategoryService categoryService, TagService tagService, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.postService = postService;
        this.commentService = commentService;
        this.postController = postController;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @GetMapping()
    public String HomePage(Model model, Pageable pageable) {
        Page<Post> posts = postService.findPublishedPosts(pageable);
        model.addAttribute("posts", posts);
        return "HomePage";
    }


    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }
    @GetMapping("/auth/register")
    public String register() {
        return "register";
    }

    @GetMapping("/admin/posts")
   // @PreAuthorize("hasRole('ADMIN')")
    public String adminPosts(Model model, Pageable pageable) {
        Page<Post> page = postService.getAllPosts(pageable);
        model.addAttribute("posts", page != null ? page : Page.empty());
        return "admin/posts";
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/posts/view/{slug}")
    public String showPost(@PathVariable String slug, Model model,Authentication auth) {
        Post post = postService.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("پست یافت نشد"));

        post.setViews(post.getViews() + 1);
        postService.CreatePost(post,auth);

        List<Comment> approvedComments = commentService.approveComment(post.getPostId());

        model.addAttribute("post", post);
        model.addAttribute("approvedComments", approvedComments);
        model.addAttribute("newComment", new Comment());

        return "DetailPosts";
    }
    @GetMapping("/admin/edit_post/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit_post(@PathVariable Integer id, Model model) {
        Post post = postService.getbyId(id);
        model.addAttribute("post", post);
        return "admin/edit_post";
    }
    @PostMapping("/controller-post/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updatePostFromForm(
            @PathVariable Integer id,
            @ModelAttribute Post post,
            RedirectAttributes redirectAttrs,
            Authentication auth
    ) {
        // پیدا کردن پست اصلی
        Post existingPost = postService.getbyId(id);
        if (existingPost == null) {
          //  redirectAttrs.addFlashAttribute("error", "پست یافت نشد.");
            return "redirect:/admin/posts";
        }


        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setExcerpt(post.getExcerpt());
        existingPost.setPostslug(post.getPostslug());
        existingPost.setStatus(post.getStatus());

        postService.CreatePost(existingPost,auth); // یا updatePost

        redirectAttrs.addFlashAttribute("message", "پست با موفقیت بروزرسانی شد.");
        return "redirect:/admin/posts";
    }
    @GetMapping("/posts/{slug}")
    public String showPostDetail(@PathVariable String slug, Model model) {
        Post post = postService.findPublishedPostbyslug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        List<Comment> comments = commentService.findApprovedByPostId(post.getPostId());

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        return "DetailPosts";
    }
    @PostMapping("/posts/{slug}/comment")
    public String addComment(
            @PathVariable String slug,
            @Valid @ModelAttribute("newComment") Comment comment
    ) {
        comment.setApproved(false); // ادمین باید تأیید کنه
        comment.setCreatedAt(LocalDateTime.now());

        commentService.createComment(comment);
        return "redirect:/posts/" + comment.getPostC().getPostslug();
    }
    @PostMapping("/comments")
    public String saveComment(
            @RequestParam Integer postId,
            @RequestParam String authorName,
            @RequestParam String authorEmail,
            @RequestParam String content
    ) {
        Post post = postService.getbyId(postId);

        Comment comment = new Comment();
        comment.setPostC(post);
        comment.setAuthorName(authorName);
        comment.setEmail(authorEmail);
        comment.setContent(content);
        comment.setApproved(false);
        comment.setCreatedAt(LocalDateTime.now());

        commentService.createComment(comment);

        return "redirect:/posts/" + post.getPostslug();
    }
    @GetMapping("/admin/create_post")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreatePostForm(Model model,Pageable pageable) {
        model.addAttribute("postDto",new PostDto("", "", "", Status.DRAFT, null,new ArrayList<>(),null));
        model.addAttribute("categories", categoryService.findAll(pageable));
        model.addAttribute("tags", tagService.findAll());
        return "admin/create_post";
    }
    @PostMapping("/admin/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public String createPost(@Valid @ModelAttribute PostDto postDto,
                             Authentication auth,
                             RedirectAttributes redirectAttrs) {
        System.out.println("✅ متد createPost فراخوانی شد!");
        System.out.println("عنوان پست: " + postDto.title());
        try {
            Post saved = postService.savePostFromDto(postDto
            );
            System.out.println("پست ذخیره شد با ID: " + saved.getPostId());
            redirectAttrs.addFlashAttribute("message", "پست با موفقیت ایجاد شد.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "خطا در ایجاد پست: " + e.getMessage());
        }
        return "redirect:/admin/posts";
    }
    @GetMapping("/admin/comments")
    public String listComments(org.springframework.ui.Model model,Pageable pageable) {
        model.addAttribute("comments", commentService.getAllCommentForAdmin(pageable));
        return "admin/comments";
    }

    // تایید یک کامنت
    @PostMapping("/admin/comments/{id}/approve")
    public String approveComment(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        commentService.approveComment(id);
        redirectAttrs.addFlashAttribute("message", "کامنت با موفقیت تأیید شد.");
        return "redirect:/admin/comments";
    }

    // حذف یک کامنت
    @PostMapping("/admin/comments/{id}/delete")
    public String deleteComment(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        commentService.DeleteComment(id);
        redirectAttrs.addFlashAttribute("message", "کامنت با موفقیت حذف شد.");
        return "redirect:/admin/comments";
    }

        @GetMapping("/admin/categories-tags")
        public String showCategoriesAndTags(Model model,Pageable pageable) {
            model.addAttribute("categories", categoryService.findAll(pageable));
            model.addAttribute("tags", tagService.findAll());
            model.addAttribute("newCategory", new Category());
            model.addAttribute("newTag", new Tag());
            return "admin/category_tags";
        }

        // ایجاد دسته‌بندی جدید
        @PostMapping("/admin/categories")
        public String createCategory(@Valid @ModelAttribute("newCategory") Category category,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttrs) {
            if (bindingResult.hasErrors()) {
                redirectAttrs.addFlashAttribute("categoryError", "نام دسته‌بندی نمی‌تواند خالی باشد.");
                return "redirect:/admin/categories-tags";
            }
            categoryService.save(category);
            redirectAttrs.addFlashAttribute("message", "دسته‌بندی با موفقیت ایجاد شد.");
            return "redirect:/admin/categories-tags";
        }

        // حذف دسته‌بندی
        @PostMapping("/admin/categories/{id}/delete")
        public String deleteCategory(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
            categoryService.delete(id);
            redirectAttrs.addFlashAttribute("message", "دسته‌بندی با موفقیت حذف شد.");
            return "redirect:/admin/categories-tags";
        }

        // ایجاد تگ جدید
        @PostMapping("/admin/tags")
        public String createTag(@Valid @ModelAttribute("newTag") Tag tag,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttrs) {
            if (bindingResult.hasErrors()) {
                redirectAttrs.addFlashAttribute("tagError", "نام تگ نمی‌تواند خالی باشد.");
                return "redirect:/admin/categories-tags";
            }
            tagService.save(tag);
            redirectAttrs.addFlashAttribute("message", "تگ با موفقیت ایجاد شد.");
            return "redirect:/admin/categories-tags";
        }

        // حذف تگ
        @PostMapping("/admin/tags/{id}/delete")
        public String deleteTag(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
            tagService.delete(id);
            redirectAttrs.addFlashAttribute("message", "تگ با موفقیت حذف شد.");
            return "redirect:/admin/categories-tags";
        }
    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    // حذف کاربر
    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        if (userService.isAdmin(id)) {
            redirectAttrs.addFlashAttribute("error", "امکان حذف ادمین وجود ندارد.");
        } else {
            userService.delete(id);
            redirectAttrs.addFlashAttribute("message", "کاربر با موفقیت حذف شد.");
        }
        return "redirect:/admin/users";
    }
    }

