package xeat.blogservice.childcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.childcategory.entity.ChildCategory;

public interface ChildCategoryRepository extends JpaRepository<ChildCategory, Long> {
}
