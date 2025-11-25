package ir.blog.blog.Service;

import ir.blog.blog.Model.Post;
import ir.blog.blog.Repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    @Autowired
    public PostServiceImpl(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public Post CreatePost(Post post) {
        return postRepo.save(post);
    }

    @Override
    public Post UpdatePost(Post post) {
        return postRepo.save(post);
    }

    @Override
    public Post DeletePost(int Id) {
        if (postRepo.existsById(Id)) {
            postRepo.deleteById(Id);
        } else {
            throw  new RuntimeException("post not found");
        }
        return null;
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
       return postRepo.findAll(pageable);
    }




}
