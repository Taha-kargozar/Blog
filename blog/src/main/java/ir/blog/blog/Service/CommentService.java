package ir.blog.blog.Service;

import ir.blog.blog.Model.Comment;
import ir.blog.blog.Model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentService {
  Comment createComment(Comment comment);

  void DeleteComment(int id);

  Page<Comment> getAllCommentForShow(Pageable pageable);

  Page<Comment> getAllCommentForAdmin(Pageable pageable);

  List<Comment> approveComment(int id);

  Optional<Comment> findById(int id);

  //List<Comment> findApprovedById(int id);

  List<Comment> findApprovedByPostId(Integer postId);
}
