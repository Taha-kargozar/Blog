package ir.blog.blog.Service;

import ir.blog.blog.Model.Post;
import ir.blog.blog.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Post CreatePost(Post post);

    Post UpdatePost(int id,Post post);

    void DeletePost(int Id);

    Page<Post> getAllPosts(Pageable pageable);

    Page<Post> getAllPostsList(User user);

    Page<Post> getbyName(String name,Pageable pageable);

    Page<Post> getbyNameAuthor(String name,Pageable pageable);

    Post getbyId(int id);
}
