package ir.blog.blog.DTO;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
        @NotBlank(message = "name.blank")
        String name,
        @NotBlank(message = "username.blank")
        String username,
                          @NotBlank(message = "pass.blank")
                          String password,
                          @NotBlank(message = "email.blank")
                          String email) {
}
