package ir.blog.blog.DTO;

import ir.blog.blog.Model.User;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CommentDto(
        @NotBlank(message = "content.blank")
        String content,
        @NotBlank(message = "date.comment.blank")
         LocalDateTime createdAt) {
}
