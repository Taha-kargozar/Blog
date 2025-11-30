package ir.blog.blog.Service;

import ir.blog.blog.Model.Tag;
import ir.blog.blog.Repository.TagRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepo tagRepo;

    public TagServiceImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }


    @Override
    public Tag save(Tag tag) {
        return tagRepo.save(tag);
    }

    @Override
    public Tag delete(int id) {
        if (tagRepo.existsById(id)) {
            tagRepo.deleteById(id);
        } else {
            throw  new RuntimeException("tag not found");
        }
        return null;
    }

    @Override
    public Page<Tag> findbyName(String name,Pageable pageable) {
        return tagRepo.findByTagName(name,pageable);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepo.findAll();
    }

    @Override
    public List<Tag> findById(List<Integer> ids) {
        return List.of();
    }

    @Override
    public Tag findBySlug(String slug) {
        return null;
    }
}
