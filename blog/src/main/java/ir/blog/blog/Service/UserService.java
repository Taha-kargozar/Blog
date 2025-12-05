package ir.blog.blog.Service;

import ir.blog.blog.Model.Role;
import ir.blog.blog.Model.User;
import ir.blog.blog.Repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
    }
    public Optional<User> findById(int id) {
        return userRepo.findById(id);
    }
    public void delete(int id) {
        userRepo.deleteById(id);
    }
    public boolean isAdmin(Integer userId) {
        User user = userRepo.findById(userId).orElse(null);
        return user != null && Role.ADMIN.equals(user.getRole());
    }
}
