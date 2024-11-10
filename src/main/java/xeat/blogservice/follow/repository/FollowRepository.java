package xeat.blogservice.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.follow.entity.Follow;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByUserUserIdAndFollowUserUserId(String userId, String followUserId);

    // 회원이 좋아요를 눌렀는지 안눌렀는지 확인 메소드
    boolean existsByUserUserIdAndFollowUserUserId(String userId, String followUserId);
}
