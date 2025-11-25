package ir.blog.blog.Service;

import ir.blog.blog.Model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
  Comment createComment(Comment comment);

  Comment DeleteComment(int id);

  Page<Comment> getAllCommentForShow(Pageable pageable);

  Page<Comment> getAllCommentForAdmin(Pageable pageable);
}
