package ir.blog.blog.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(
        @NotBlank(message = "name.blank")
        String Name,
        @NotBlank(message = "username.blank")
        String username,
                          @NotBlank(message = "pass.blank")
                          String password,
                          @NotBlank(message = "email.blank")
                          String Email,
        @NotBlank(message = "repass.blank")
        String repass) {
}
