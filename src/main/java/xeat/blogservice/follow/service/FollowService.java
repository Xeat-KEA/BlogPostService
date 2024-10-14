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

@Service
@RequiredArgsConstructor
public class FollowService {

    private final BlogRepository blogRepository;
    private final FollowRepository followRepository;

    @Transactional
    public Response<?> recommend(FollowRequestDto followRequestDto) {

        Long userId = followRequestDto.getUserId();
        Long followUserId = followRequestDto.getFollowUserId();
        
        Blog blog = blogRepository.findById(userId).get();

        // 팔로우 요청일 경우
        if (!followRepository.existsByUserIdAndFollowUserId(userId, followUserId)) {
            Follow follow = Follow.builder()
                    .user(blogRepository.findById(userId).get())
                    .followUser(blogRepository.findByUserId(followUserId).get())
                    .build();
            blog.plusFollowCount();
            blogRepository.save(blog);
            followRepository.save(follow);
            return new Response<>(200, "사용자 팔로우 요청 성공", FollowResponseDto.toDto(blog.getFollowCount()));
        }

        // 팔로우 요청 취소일 경우
        else {
            followRepository.delete(followRepository.findByUserIdAndFollowUserId(userId, followUserId).get());
            blog.minusFollowCount();
            blogRepository.save(blog);
            return new Response<>(200, "사용자 팔로우 요청 취소 성공", FollowResponseDto.toDto(blog.getFollowCount()));
        }
    }
}
