package ir.blog.blog.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CommentId;

    private String content;
    private User authorPost;
    private boolean approved = false;
    private LocalDateTime createdAt;
    private Post postC;

    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @ManyToOne
    public User getAuthorPost() {
        return authorPost;
    }

    public void setAuthorPost(User authorPost) {
        this.authorPost = authorPost;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    @CreationTimestamp
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPostC() {
        return postC;
    }

    public void setPostC(Post postC) {
        this.postC = postC;
    }
}
