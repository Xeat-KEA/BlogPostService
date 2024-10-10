package xeat.blogservice.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.blog.dto.BlogCreateRequestDto;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.service.BlogService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/blog/create")
    public Response<Blog> createBlog(@RequestBody BlogCreateRequestDto blogCreateRequestDto) {
        return blogService.create(blogCreateRequestDto);
    }
}
