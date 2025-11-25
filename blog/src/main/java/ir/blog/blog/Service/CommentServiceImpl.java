package ir.blog.blog.Service;

import ir.blog.blog.Model.Comment;
import ir.blog.blog.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Comment DeleteComment(int id) {
        if (commentRepo.existsById(id)) {
            commentRepo.deleteById(id);
        } else {
            throw  new RuntimeException("comment not found");
        }
        return null;
    }

    @Override
    public Page<Comment> getAllCommentForShow(Pageable pageable) {
        return commentRepo.findByApprovedTrueOrderByCreatedAtAsc(pageable);
    }

    @Override
    public Page<Comment> getAllCommentForAdmin(Pageable pageable) {
        return commentRepo.findAll(pageable);
    }
}
