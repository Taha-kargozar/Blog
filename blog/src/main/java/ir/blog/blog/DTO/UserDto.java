package ir.blog.blog.DTO;

import ir.blog.blog.Model.Role;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

public record UserDto (
        @NotBlank(message = "user.dto.name.blank")
        String Name,
        @NotBlank(message = "user.dto.username.blank")
         String UserName,
        @NotBlank(message = "user.dto.email.blank")
         String email,
         Date createdAt,
         Role role) {

}
