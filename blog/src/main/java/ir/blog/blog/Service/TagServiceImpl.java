package ir.blog.blog.Service;

import ir.blog.blog.Model.Tag;
import ir.blog.blog.Repository.TagRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepo.findAll(pageable);
    }

    @Override
    public Optional<Tag> findbyName(String name,Pageable pageable) {
        return tagRepo.findByTagName(name,pageable);
    }
}
