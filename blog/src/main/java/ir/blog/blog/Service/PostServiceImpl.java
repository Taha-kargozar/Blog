package ir.blog.blog.Service;

import ir.blog.blog.Model.Post;
import ir.blog.blog.Model.Status;
import ir.blog.blog.Model.User;
import ir.blog.blog.Repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    @Autowired
    public PostServiceImpl(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

   @Override
   public Post CreatePost(Post post) {
       post.setPostslug(post.getTitle());
       post.setStatus(post.getStatus() != null ? post.getStatus() : Status.DRAFT);
       post.setCreatedAt(LocalDateTime.now());
       if (Status.PUBLISHED.equals(post.getStatus())) {
           post.setPublishedAt(LocalDateTime.now());
       }
       return postRepo.save(post);
   }

    @Override
    public Post UpdatePost(int id, Post post) {
        return postRepo.findById(id)
                .map(existing -> {
                    existing.setTitle(post.getTitle());
                    existing.setContent(post.getContent());
                    existing.setPostslug(post.getTitle()); // یا فقط اگر عنوان عوض شد
                    existing.setStatus(post.getStatus());
                    existing.setUpdatedAt(LocalDateTime.now());
                    if (post.getStatus().PUBLISHED.equals(post.getStatus()) && existing.getPublishedAt() == null) {
                        existing.setPublishedAt(LocalDateTime.now());
                    }
                    return postRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }
    @Override
    public void DeletePost(int Id) {
        if (postRepo.existsById(Id)) {
            postRepo.deleteById(Id);
        } else {
            throw  new RuntimeException("post not found");
        }
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
       return postRepo.findAll(pageable);
    }

    @Override
    public Page<Post> getAllPostsList(User user) {
        return postRepo.findAllByAuthor(user);
    }

    @Override
    public Page<Post> getbyName(String name, Pageable pageable) {
        return postRepo.findByTitle(name,pageable);
    }

    @Override
    public Page<Post> getbyNameAuthor(String name, Pageable pageable) {
        return postRepo.findByAuthorName(name,pageable);
    }

    @Override
    public Post getbyId(int id) {
        return postRepo.findById(id)
                .orElseThrow();
    }

}
