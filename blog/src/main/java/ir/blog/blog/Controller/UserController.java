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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @PostMapping("/register")
    public String register(@Valid RegisterDto registerDto) {
        if(!registerDto.password().equals(registerDto.repass())) {
            return "redirect:/register?error=true";
        }
        User user = new User();
        user.setUserName(registerDto.username());
        user.setName(registerDto.Name());
        user.setEmail(registerDto.Email());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setRole(Role.USER);
        userService.save(user);

        return "login";
    }
}
