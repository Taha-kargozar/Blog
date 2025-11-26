package ir.blog.blog.Service;

import ir.blog.blog.Model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagService {
    Tag save(Tag tag);
    Tag delete(int id);
    Page<Tag> findAll(Pageable pageable);
    Optional<Tag> findbyName(String name,Pageable  pageable);
}
