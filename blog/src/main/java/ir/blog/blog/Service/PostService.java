package ir.blog.blog.Service;

import ir.blog.blog.Model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Post CreatePost(Post post);

    Post UpdatePost(Post post);

    Post DeletePost(int Id);

    Page<Post> getAllPosts(Pageable pageable);

    Page<Post> getbyName(String name,Pageable pageable);

    Page<Post> getbyNameAuthor(String name,Pageable pageable);
}
