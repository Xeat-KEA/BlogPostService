package xeat.blogservice.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.Response;
import xeat.blogservice.notice.service.NoticeService;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PutMapping("/blog/notice/check/{blogId}")
    public Response<Blog> checkNotice(@PathVariable Long blogId) {
        return noticeService.checkNotice(blogId);
    }
}
