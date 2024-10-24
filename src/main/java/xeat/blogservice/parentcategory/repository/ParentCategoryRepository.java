package xeat.blogservice.parentcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.parentcategory.entity.ParentCategory;

import java.util.List;

public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long> {

    List<ParentCategory> findAllByBlogId(Long blogId);
}
