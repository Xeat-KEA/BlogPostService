package xeat.blogservice.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.Response;
import xeat.blogservice.notice.service.NoticeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list/{blogId}")
    public Response<?> getNoticeList(@PathVariable Long blogId)  {
        return noticeService.getNoticeList(blogId);
    }

    @PutMapping("/check/{blogId}")
    public Response<Blog> checkNotice(@PathVariable Long blogId) {
        return noticeService.checkNotice(blogId);
    }
}
