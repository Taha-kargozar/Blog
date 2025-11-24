package ir.blog.blog.Repository;

import ir.blog.blog.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository <Tag,Integer> {

}
