package ir.blog.blog.Repository;

import ir.blog.blog.Model.Post;
import ir.blog.blog.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

public interface PostRepo extends JpaRepository<Post,Integer> {
    Page<Post> findByTitle(String name, Pageable pageable);
    Page<Post> findByAuthorName(String name,Pageable pageable);
    Page<Post> findAllByAuthor(User user);
}
