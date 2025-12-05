package ir.blog.blog.DTO;

import ir.blog.blog.Model.Post;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CommentDto(
        @NotBlank(message = "content.blank")
        String content,
         LocalDateTime created,
                 @NotBlank
                 String authorName,
                 String Email,
                 LocalDateTime createdAt,
        Post postC) {
}
