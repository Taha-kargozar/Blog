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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/controller-user")
public class UserController {
    private final UserService userService;
   private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAll(Pageable pageable) {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto dto, BindingResult result) {

        User user = new User();
        user.setUsername(dto.username());
        user.setName(dto.Name());
        user.setEmail(dto.Email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER);

        userService.save(user);

        return ResponseEntity.ok("ثبت‌نام با موفقیت انجام شد.");

    }
}
