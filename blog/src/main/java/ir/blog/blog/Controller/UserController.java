package ir.blog.blog.Controller;

import ir.blog.blog.DTO.RegisterDto;
import ir.blog.blog.Model.Role;
import ir.blog.blog.Model.User;
import ir.blog.blog.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/controller-user")
@Validated
public class UserController {
    private final UserService userService;
   private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(Pageable pageable) {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

   @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto dto) {
        try {
            User user = new User();
            user.setUsername(dto.username());
            user.setName(dto.name());
            user.setEmail(dto.email());
            user.setPassword(passwordEncoder.encode(dto.password()));
            user.setRole(Role.USER);

            System.out.println("🟡 قبل از save: " + user);
            userService.save(user);
            System.out.println("🟡 قبل از save: " + user);
            return ResponseEntity.ok(Map.of("message", "ثبت‌نام با موفقیت انجام شد."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "خطا در ثبت‌نام: " + e.getMessage()));
        }
    }




}
