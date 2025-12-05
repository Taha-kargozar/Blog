package ir.blog.blog.DTO;

import ir.blog.blog.Model.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record PostDto(
        @NotBlank(message = "post.dto.title.blank")
        String title,
        String content,
        String postslug,
        Status status,
        String excerpt,
        List<Integer> PostTagID,
        Integer categoryID
        ) {
}
