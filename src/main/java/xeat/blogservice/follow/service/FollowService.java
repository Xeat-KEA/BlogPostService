package xeat.blogservice.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.follow.dto.FollowRequestDto;
import xeat.blogservice.follow.dto.FollowResponseDto;
import xeat.blogservice.follow.entity.Follow;
import xeat.blogservice.follow.repository.FollowRepository;
import xeat.blogservice.global.Response;
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
    public Response<FollowResponseDto> recommend(String userId, String followUserId) {
        
        Blog blog = blogRepository.findByUserId(userId).get();
        Blog followUser = blogRepository.findByUserId(followUserId).get();


        if (!Objects.equals(blog.getUserId(), userId)) {
            // 팔로우 요청일 경우
            if (!followRepository.existsByUserUserIdAndFollowUserUserId(userId, followUserId)) {
                Follow follow = Follow.builder()
                        .user(blog)
                        .followUser(followUser)
                        .build();

                blog.plusFollowCount();
                blog.updateNoticeCheckFalse();

                blogRepository.save(blog);
                followRepository.save(follow);

                // 알림 table에 추가
                Notice notice = Notice.builder()
                        .blog(blog)
                        .sentUser(followUser)
                        .noticeCategory(NoticeCategory.FOLLOW)
                        .build();

                noticeRepository.save(notice);

                return new Response<>(200, "사용자 팔로우 요청 성공", FollowResponseDto.toDto(blog));
            }


            // 팔로우 요청 취소일 경우
            else {
                followRepository.delete(followRepository.findByUserUserIdAndFollowUserUserId(userId, followUserId).get());
                blog.minusFollowCount();
                blogRepository.save(blog);
                return new Response<>(200, "사용자 팔로우 요청 취소 성공", FollowResponseDto.toDto(blog));
            }
        }

        else {
            return new Response<>(400, "본인 블로그는 팔로우 할 수 없습니다", null);
        }
    }
}
