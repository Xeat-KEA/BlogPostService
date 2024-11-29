package xeat.blogservice.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.follow.entity.Follow;
import xeat.blogservice.follow.repository.FollowRepository;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;
import xeat.blogservice.notice.repository.NoticeRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final BlogRepository blogRepository;
    private final FollowRepository followRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public Response<?> recommend(Long blogId, String userId) {
        
        Blog targetUser = blogRepository.findById(blogId).get();
        Blog followUser = blogRepository.findByUserId(userId).get();


        if (!Objects.equals(targetUser.getUserId(), followUser.getUserId())) {
            // 팔로우 요청일 경우
            if (!followRepository.existsByTargetUserAndFollowUser(targetUser, followUser)) {
                Follow follow = Follow.builder()
                        .targetUser(targetUser)
                        .followUser(followUser)
                        .build();

                targetUser.plusFollowCount();
                targetUser.updateNoticeCheckFalse();

                blogRepository.save(targetUser);
                followRepository.save(follow);

                // 알림 table에 추가
                Notice notice = Notice.builder()
                        .blog(targetUser)
                        .sentUser(followUser)
                        .noticeCategory(NoticeCategory.FOLLOW)
                        .build();

                noticeRepository.save(notice);

                return new Response<>(200, "사용자 팔로우 요청 성공", null);
            }


            // 팔로우 요청 취소일 경우
            else {
                followRepository.delete(followRepository.findByTargetUserAndFollowUser(targetUser, followUser).get());
                targetUser.minusFollowCount();
                blogRepository.save(targetUser);
                return new Response<>(200, "사용자 팔로우 요청 취소 성공", null);
            }
        }

        else {
            return new Response<>(400, "본인 블로그는 팔로우 할 수 없습니다", null);
        }
    }
}
