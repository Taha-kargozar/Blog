package ir.blog.blog.Repository;

import ir.blog.blog.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
