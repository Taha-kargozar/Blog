package ir.blog.blog.Repository;

import ir.blog.blog.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
    Page<Category> findByNameCategory(String name, Pageable pageable);
}
