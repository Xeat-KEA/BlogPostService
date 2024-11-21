package xeat.blogservice.recommend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.recommend.entity.Recommend;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Optional<Recommend> findByArticleIdAndUserUserId(Long articleId, String userId);

    Boolean existsByArticleIdAndUserUserId(Long articleId, String userId);
}
