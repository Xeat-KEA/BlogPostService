package xeat.blogservice.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.dto.*;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.follow.repository.FollowRepository;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.image.service.ImageService;

import java.util.Base64;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
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
    public Long getBlogIdFromUser(String userId) {
        return blogRepository.findByUserId(userId).get().getId();
    }

    @Transactional
    public Response<BlogNoticeCheckResponseDto> getNoticeCheck(String userId) {
        Blog blog = blogRepository.findByUserId(userId).get();
        return Response.success(BlogNoticeCheckResponseDto.toDto(blog));
    }


    @Transactional
    public Response<BlogLoginHomeResponseDto> getLoginBlogHome(String userId, Long blogId) {

        Blog blog = blogRepository.findById(blogId).get();

        //사용자 티어 받기
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(blog.getUserId());

        boolean followCheck = false;

        if (userId != null) {
            Blog followUser = blogRepository.findByUserId(userId).get();
            followCheck = followRepository.existsByTargetUserAndFollowUser(blog, followUser);
        }

        String mainContent = null;
        if (blog.getMainContent() != null) {
            mainContent = Base64.getEncoder().encodeToString(blog.getMainContent().getBytes());
        }

        return Response.success(BlogLoginHomeResponseDto.toDto(blog, mainContent, userInfo, followCheck));
    }

    @Transactional
    public Response<BlogMainContentResponseDto> getMainContent(String userId) {
        Blog blog = blogRepository.findByUserId(userId).get();

        String mainContent = null;
        if (blog.getMainContent() != null) {
            mainContent = Base64.getEncoder().encodeToString(blog.getMainContent().getBytes());
        }

        return Response.success(BlogMainContentResponseDto.toDto(blog, mainContent));
    }

    @Transactional
    public Response<BlogEditResponseDto> getEditMainContent(String userId) throws Exception {

        Blog blog = blogRepository.findByUserId(userId).get();

        String mainContent = null;
        if (blog.getMainContent() != null) {
            mainContent = Base64.getEncoder().encodeToString(blog.getMainContent().getBytes());
        }

        List<String> originalImageList = minioImageService.getOriginalImageList(blog.getMainContent());

        return Response.success(BlogEditResponseDto.toDto(blog, mainContent, originalImageList));
    }

    @Transactional
    // 블로그 게시판 생성
    public Response<?> create(String userId) {
        Blog blog = Blog.builder()
                .userId(userId)
                .build();
        blogRepository.save(blog);
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(blog.getUserId());
        log.info("블로그 생성 완료, 블로그 ID = {}, 블로그 사용자 이름 = {}", blog.getId(), userInfo.getNickName());
        return new Response<>(200, userInfo.getNickName() + " 사용자 " + "블로그 생성 완료", null);
    }

    @Transactional
    // 블로그 소개글 수정
    public Response<BlogMainContentResponseDto> editMainContent(String userId, BlogEditRequestDto blogEditRequestDto) throws Exception {

        Blog blog = blogRepository.findByUserId(userId).get();
        String updateMainContent = minioImageService.editBlogImage(blogEditRequestDto.getMainContent(), blogEditRequestDto.getOriginalImageList());
        blog.updateMainContent(updateMainContent);

        Blog updateBlog = blogRepository.save(blog);

        String mainContent = null;
        if (updateBlog.getMainContent() != null) {
            mainContent = Base64.getEncoder().encodeToString(updateBlog.getMainContent().getBytes());
        }

        return Response.success(BlogMainContentResponseDto.toDto(updateBlog, mainContent));
    }

    @Transactional
    public Response<BlogNoticeCheckResponseDto> editNotice(String userId) {
        Blog blog = blogRepository.findByUserId(userId).get();

        if (!blog.getNoticeCheck()) {
            blog.updateNoticeCheckTrue();
            blogRepository.save(blog);
        }
        return Response.success(BlogNoticeCheckResponseDto.toDto(blog));
    }

    @Transactional
    public Response<?> deleteBlog(String userId) {
        blogRepository.deleteByUserId(userId);
        return new Response<>(200, "게시글 삭제 완료", null);
    }
}