package ir.blog.blog.DTO;

import ir.blog.blog.Model.Post;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CommentDto(
        @NotBlank(message = "content.blank")
        String content,
        @NotBlank(message = "date.comment.blank")
         LocalDateTime created,
                 String authorName,
                 String Email,
                 LocalDateTime createdAt) {
}
