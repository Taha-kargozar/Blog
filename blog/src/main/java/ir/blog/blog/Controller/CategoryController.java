package ir.blog.blog.Controller;

import ir.blog.blog.DTO.CategoryDto;
import ir.blog.blog.Model.Category;
import ir.blog.blog.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/controller-category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/create")
    public ResponseEntity<Category> CreateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Category category = new Category();
        category.setNameCategory(categoryDto.nameCategory());
        category.setDescCategory(categoryDto.descCategory());
        categoryService.save(category);
        return ResponseEntity.ok().body(category);
    }
    @GetMapping
    public ResponseEntity<Page<Category>> findAllCategory(Pageable pageable) {
        Page <Category> category = categoryService.findAll(pageable);
        return ResponseEntity.ok().body(category);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> findByIdCategory(@PathVariable int id) {
        Optional<Category> category = categoryService.findById(id);
        return ResponseEntity.ok().body(category);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Category> UpdateCategory(@PathVariable int id,@RequestBody @Valid CategoryDto categoryDto) {
        Optional<Category> Category = categoryService.findById(id);
        Category categoryToUpdate = Category.get();
        categoryToUpdate.setNameCategory(categoryDto.nameCategory());
        categoryToUpdate.setDescCategory(categoryDto.descCategory());
        Category updatedCategory = categoryService.save(categoryToUpdate);
        if (Category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCategory);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
