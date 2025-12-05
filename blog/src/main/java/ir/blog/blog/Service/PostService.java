package ir.blog.blog.Service;

import ir.blog.blog.DTO.PostDto;
import ir.blog.blog.Model.Post;
import ir.blog.blog.Model.Tag;
import ir.blog.blog.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post CreatePost(Post post, Authentication auth);

    Post UpdatePost(int id,Post post);

    void DeletePost(int Id);

    Page<Post> getAllPosts(Pageable pageable);

    Page<Post> getAllPostsList(User user,Pageable pageable);

    Page<Post> getbyName(String name,Pageable pageable);

    Page<Post> getbyNameAuthor(String name,Pageable pageable);

    Post getbyId(int id);

    Optional<Post> findBySlug(String slug);

    Page<Post> findPublishedPosts(Pageable pageable);

    Optional<Post> findPublishedPostbyslug(String slug);

    List<Post> findByTag(Tag tag);

    Post savePostFromDto(PostDto dto
            //, Authentication auth
    );
}
