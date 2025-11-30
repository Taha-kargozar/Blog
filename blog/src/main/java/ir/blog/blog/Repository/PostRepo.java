package ir.blog.blog.Repository;

import ir.blog.blog.Model.Post;
import ir.blog.blog.Model.Status;
import ir.blog.blog.Model.Tag;
import ir.blog.blog.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface PostRepo extends JpaRepository<Post,Integer> {
    Page<Post> findByTitle(String name, Pageable pageable);
    Page<Post> findByAuthorName(String name,Pageable pageable);
    Page<Post> findAllByAuthor(User user);
    Optional<Post> findByPostslug (String slug);
    Page<Post> findByStatus(Status status,Pageable pageable);
    Page<Post> findByStatusaAndPostslug(Status status,String slug,Pageable pageable);
    List<Post> findByPostTag(Tag tag);
    void deleteByPostId(int id);
}
