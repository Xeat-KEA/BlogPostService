package xeat.blogservice.recommend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.recommend.entity.Recommend;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Optional<Recommend> findByArticleIdAndUserId(Long articleId, Long userId);

    Boolean existsByArticleIdAndUserId(Long articleId, Long userId);
}
