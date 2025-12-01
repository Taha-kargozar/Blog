package ir.blog.blog.Repository;

import ir.blog.blog.Model.Comment;
import ir.blog.blog.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
    Page<Comment> findByApprovedFalse(Pageable pageable);
    Page<Comment> findByApprovedTrueOrderByCreatedAtAsc(Pageable pageable);
}
