package xeat.blogservice.report.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.global.Response;
import xeat.blogservice.report.dto.*;
import xeat.blogservice.report.service.UserReportService;

@RestController
@RequiredArgsConstructor
public class UserReportController {

    private final UserReportService userReportService;

    @PostMapping("/blog/board/report/{blogId}")
    public Response<BlogReportResponseDto> reportBlog(@PathVariable Long blogId, @RequestBody ReportRequestDto reportRequestDto) {

        return userReportService.reportBlog(blogId, reportRequestDto);
    }

    @PostMapping("/blog/board/article/report/{articleId}")
    public Response<ArticleReportResponseDto> reportArticle(@PathVariable Long articleId, @RequestBody ReportRequestDto reportRequestDto) {

        return userReportService.reportArticle(articleId, reportRequestDto);
    }

    @PostMapping("/blog/board/reply/report/{replyId}")
    public Response<ReplyReportResponseDto> reportReply(@PathVariable Long replyId, @RequestBody ReportRequestDto reportRequestDto) {
        return userReportService.reportReply(replyId, reportRequestDto);
    }
}
