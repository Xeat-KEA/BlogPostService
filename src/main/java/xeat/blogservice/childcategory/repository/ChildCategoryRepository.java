package xeat.blogservice.childcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.childcategory.entity.ChildCategory;

import java.util.List;

public interface ChildCategoryRepository extends JpaRepository<ChildCategory, Long> {

    List<ChildCategory> findAllByParentCategoryId(Long parentCategoryId);
}
