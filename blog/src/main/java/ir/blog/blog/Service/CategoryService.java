package ir.blog.blog.Service;

import ir.blog.blog.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
    Optional<Category> findById(int id);
    Category save(Category category);
    void delete(int id);
    Page<Category> findbyName(String name, Pageable pageable);
}
