package ir.blog.blog.Service;

import ir.blog.blog.Model.Category;
import ir.blog.blog.Repository.CategoryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
   private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void delete(int id) {
            categoryRepo.deleteById(id);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepo.findById(id);
    }

    @Override
    public Optional<Category> findbyName(String name, Pageable pageable) {
        return categoryRepo.findByNameCategory(name,pageable);
    }
}


