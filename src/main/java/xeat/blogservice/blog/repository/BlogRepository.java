package xeat.blogservice.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.blog.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
