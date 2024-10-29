package xeat.blogservice.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.blog.dto.BlogCreateRequestDto;
import xeat.blogservice.blog.dto.BlogEditRequestDto;
import xeat.blogservice.blog.dto.BlogMainContentResponseDto;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.service.BlogService;
import xeat.blogservice.global.Response;
import xeat.blogservice.blog.dto.BlogNoticeCheckResponseDto;

@Tag(name = "블로그", description = "블로그 관련 API")
@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @Operation(summary = "블로그 알림 확인 여부 조회", description = "사용자가 블로그로 온 알림을 확인했는지 여부를 파악하기 위해 필요한 API")
    @GetMapping("/notice/check/{blogId}")
    public Response<BlogNoticeCheckResponseDto> getNoticeCheck(@PathVariable Long blogId) {
        return blogService.getNoticeCheck(blogId);
    }

    @Operation(summary = "블로그 소개 글 조회", description = "블로그 소개 글 수정 페이지로 넘어갈 때 필요한 API")
    @GetMapping("/mainContent/{blogId}")
    public Response<BlogMainContentResponseDto> getMainContent(@PathVariable Long blogId) {
        return blogService.getMainContent(blogId);
    }

    @Operation(summary = "블로그 생성", description = "블로그를 생성할 때 필요한 API")
    @PostMapping("/create")
    public Response<Blog> createBlog(@RequestBody BlogCreateRequestDto blogCreateRequestDto) {
        return blogService.create(blogCreateRequestDto);
    }

    @Operation(summary = "블로그 수정", description = "블로그 소개 글을 수정할 떄 필요한 API")
    @PutMapping("/home/{blogId}")
    public Response<Blog> editMainContent(@PathVariable Long blogId, @RequestBody BlogEditRequestDto blogEditRequestDto) {
        return blogService.editMainContent(blogId, blogEditRequestDto);
    }
}
