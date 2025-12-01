package ir.blog.blog.Controller;

import ir.blog.blog.DTO.TagDto;
import ir.blog.blog.Model.Tag;
import ir.blog.blog.Service.PostService;
import ir.blog.blog.Service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/controller-tags")
public class TagController {
    private final TagService tagService;
    private final PostService postService;

    public TagController(TagService tagService, PostService postService) {
        this.tagService = tagService;
        this.postService = postService;
    }

    @PostMapping("/save")
    public ResponseEntity<Tag> save(@Valid @RequestBody TagDto tagDto) {
        Tag tag = new Tag();
        tag.setTagName(tagDto.TagName());
        Tag saveTag = tagService.save(tag);
        return ResponseEntity.ok(saveTag);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAll() {
        List<Tag> tags = tagService.findAll();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(@PathVariable int id) {
        Optional<Tag> tag = tagService.findById(id);
        return tag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Tag> update(@PathVariable int id, @Valid @RequestBody TagDto tagDto) {
        Optional<Tag> optionalTag = tagService.findById(id);
        if (optionalTag.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tag tagToUpdate = optionalTag.get();
        tagToUpdate.setTagName(tagDto.TagName());
        Tag updateTag = tagService.save(tagToUpdate);
        return ResponseEntity.ok(updateTag);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        tagService.delete(id);
        return ResponseEntity.ok().build();
    }
}
