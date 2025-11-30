package ir.blog.blog.Service;

import ir.blog.blog.Model.Comment;
import ir.blog.blog.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    public void DeleteComment(int id) {
        if (commentRepo.existsById(id)) {
            commentRepo.deleteById(id);
        } else {
            throw  new RuntimeException("comment not found");
        }
    }

    @Override
    public Page<Comment> getAllCommentForShow(Pageable pageable) {
        return commentRepo.findByApprovedTrueOrderByCreatedAtAsc(pageable);
    }

    @Override
    public Page<Comment> getAllCommentForAdmin(Pageable pageable) {
        return commentRepo.findAll(pageable);
    }
    @Override
    public void approveComment(int id) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        comment.setApproved(true);
        commentRepo.save(comment);
    }

    @Override
    public List<Comment> findByPostId(int postId) {
        return commentRepo.findByPostCPostId(postId);
    }

    @Override
    public Optional<Comment> findById(int id) {
        return commentRepo.findById(id);
    }
}
