package ir.blog.blog.Repository;

import ir.blog.blog.Model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Integer> {
    Page<Post> findByTitle(String name, Pageable pageable);
    Page<Post> findByAuthorName(String name,Pageable pageable);
}
