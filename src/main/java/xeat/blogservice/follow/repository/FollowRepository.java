package xeat.blogservice.follow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.follow.entity.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByTargetUserAndFollowUser(Blog targetUser, Blog followUser);

    Page<Follow> findAllByTargetUser(Pageable pageable, Blog targetUser);

    // 회원이 좋아요를 눌렀는지 안눌렀는지 확인 메소드
    boolean existsByTargetUserAndFollowUser(Blog targetUser, Blog followUser);
}
