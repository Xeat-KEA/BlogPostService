package xeat.blogservice.parentcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.parentcategory.entity.ParentCategory;

public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long> {
}
