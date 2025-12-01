package ir.blog.blog.DTO;

import ir.blog.blog.Model.Category;
import ir.blog.blog.Model.Status;
import ir.blog.blog.Model.Tag;
import ir.blog.blog.Model.User;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record PostDto(
        @NotBlank(message = "post.dto.title.blank")
        String title,
        String content,
        String postslug,
        Status status,
        @NotBlank(message = "post.dto.author.blank")
         User author,
        String excerpt,
        @NotBlank(message = "post.dto.date.blank")
         LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime publishedAt,
        int views,
        List<Tag> PostTag,
        Category category) {
}
