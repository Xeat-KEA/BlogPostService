package xeat.blogservice.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.blog.dto.BlogCreateRequestDto;
import xeat.blogservice.blog.dto.BlogEditRequestDto;
import xeat.blogservice.blog.dto.BlogMainContentResponseDto;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.service.BlogService;
import xeat.blogservice.global.Response;
import xeat.blogservice.blog.dto.BlogNoticeCheckResponseDto;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/mainContent/{blogId}")
    public Response<BlogMainContentResponseDto> getMainContent(@PathVariable Long blogId) {
        return blogService.getMainContent(blogId);
    }

    @PostMapping("/create")
    public Response<Blog> createBlog(@RequestBody BlogCreateRequestDto blogCreateRequestDto) {
        return blogService.create(blogCreateRequestDto);
    }

    @PutMapping("/home/{blogId}")
    public Response<Blog> editMainContent(@PathVariable Long blogId, @RequestBody BlogEditRequestDto blogEditRequestDto) {
        return blogService.editMainContent(blogId, blogEditRequestDto);
    }

    @GetMapping("/notice/check/{blogId}")
    public Response<BlogNoticeCheckResponseDto> getNoticeCheck(@PathVariable Long blogId) {
        return blogService.getNoticeCheck(blogId);
    }
}
