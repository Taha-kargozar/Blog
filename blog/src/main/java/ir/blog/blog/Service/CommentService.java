package ir.blog.blog.Service;

import ir.blog.blog.Model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentService {
  Comment createComment(Comment comment);

  void DeleteComment(int id);

  Page<Comment> getAllCommentForShow(Pageable pageable);

  Page<Comment> getAllCommentForAdmin(Pageable pageable);

  void approveComment(int id);

  List<Comment> findByPostId(int postId);

  Optional<Comment> findById(int id);
}
