package ir.blog.blog.Controller;

import ir.blog.blog.DTO.CommentDto;
import ir.blog.blog.Model.Comment;
import ir.blog.blog.Model.Post;
import ir.blog.blog.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;

@RestController
@RequestMapping("/controller-comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable int id) {
        Optional<Comment> comment = commentService.findById(id);
        return comment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<Comment> saveComment(@Valid @RequestBody CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.content());
        comment.setAuthorName(commentDto.authorName());
        comment.setApproved(FALSE);
        comment.setEmail(commentDto.Email());
        comment.setPostC(commentDto.postC());
        Comment savedComment = commentService.createComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.DeleteComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public Page<Comment> findall(Pageable pageable) {
        return commentService.getAllCommentForShow(pageable);
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Comment> findallForADMIN(Pageable pageable) {
        return commentService.getAllCommentForAdmin(pageable);
    }
}
