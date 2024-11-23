package xeat.blogservice.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.dto.*;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.follow.repository.FollowRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.image.service.ImageService;


@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final FollowRepository followRepository;
    private final UserFeignClient userFeignClient;
    private final ImageService minioImageService;

    @Transactional
    public Response<BlogIdResponseDto> getBlogId(String userId) {
        Long blogId = blogRepository.findByUserId(userId).get().getId();
        return Response.success(BlogIdResponseDto.toDto(blogId));
    }


    @Transactional
    public Response<BlogLoginHomeResponseDto> getLoginBlogHome(String userId, Long blogId) {

        Blog blog = blogRepository.findById(blogId).get();

        //사용자 티어 받기
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(blog.getUserId());

        boolean followCheck = followRepository.existsByUserUserIdAndFollowUserUserId(blog.getUserId(), userId);

        return Response.success(BlogLoginHomeResponseDto.toDto(blog, userInfo, followCheck));

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
    public Response<Blog> editMainContent(String userId, BlogEditRequestDto blogEditRequestDto) throws Exception{
        if (blogEditRequestDto.getDeleteImageUrls() != null) {
            minioImageService.deleteImage(blogEditRequestDto.getDeleteImageUrls());
        }
        String updateMainContent = minioImageService.editBlogImage(blogEditRequestDto.getMainContent());
        Blog blog = blogRepository.findByUserId(userId).get();
        blog.updateMainContent(updateMainContent);
        return Response.success(blogRepository.save(blog));
    }

    @Transactional
    public Response<BlogNoticeCheckResponseDto> getNoticeCheck(String userId) {
        Blog blog = blogRepository.findByUserId(userId).get();
        return Response.success(BlogNoticeCheckResponseDto.toDto(blog));
    }
}