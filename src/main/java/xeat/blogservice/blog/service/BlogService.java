package xeat.blogservice.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.dto.*;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.follow.repository.FollowRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.global.userclient.UserFeignClient;
import xeat.blogservice.global.userclient.UserInfoResponseDto;


@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final FollowRepository followRepository;
    private final UserFeignClient userFeignClient;

    @Transactional
    public Response<BlogLoginHomeResponseDto> getLoginBlogHome(String userId, Long blogId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);

        //사용자 티어 받기
        String rank = userInfo.getRank(userInfo.getTotalScore());

        Blog blog = blogRepository.findById(blogId).get();
        boolean followCheck = followRepository.existsByUserUserIdAndFollowUserUserId(blog.getUserId(), userId);

        return Response.success(BlogLoginHomeResponseDto.toDto(blog, userInfo, rank, followCheck));

    }

    @Transactional
    public Response<BlogMainContentResponseDto> getMainContent(String userId) {
        Blog blog = blogRepository.findByUserId(userId).get();
        return Response.success(BlogMainContentResponseDto.toDto(blog));
    }


    @Transactional
    // 블로그 게시판 생성
    public Response<Blog> create(String userId) {
        Blog blog = Blog.builder()
                .userId(userId)
                .build();
        return Response.success(blogRepository.save(blog));
    }

    @Transactional
    // 블로그 소개글 수정
    public Response<Blog> editMainContent(String userId, BlogEditRequestDto blogEditRequestDto) {
        Blog blog = blogRepository.findByUserId(userId).get();
        blog.updateMainContent(blogEditRequestDto.getMainContent());
        return Response.success(blogRepository.save(blog));
    }

    @Transactional
    public Response<BlogNoticeCheckResponseDto> getNoticeCheck(String userId) {
        Blog blog = blogRepository.findByUserId(userId).get();
        return Response.success(BlogNoticeCheckResponseDto.toDto(blog));
    }
}