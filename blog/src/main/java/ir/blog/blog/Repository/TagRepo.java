package ir.blog.blog.Repository;

import ir.blog.blog.Model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepo extends JpaRepository <Tag,Integer> {
    Optional<Tag> findByTagName(String name, Pageable pageable);
}
