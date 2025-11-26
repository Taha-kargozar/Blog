package ir.blog.blog.Service;

import ir.blog.blog.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    public Category save(Category category);
    Category delete(int id);
    Page<Category> findAll(Pageable pageable);
    Optional<Category> findbyName(String name,Pageable pageable);
}
