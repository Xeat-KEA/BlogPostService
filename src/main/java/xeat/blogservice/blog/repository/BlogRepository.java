package xeat.blogservice.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.blog.entity.Blog;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findByUserId(String userId);
}
