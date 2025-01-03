package xeat.blogservice.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.report.dto.*;
import xeat.blogservice.report.service.UserReportService;

@Tag(name = "신고", description = "블로그 신고, 게시글 신고, 댓글 신고 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class UserReportController {

    private final UserReportService userReportService;

    @Operation(summary = "블로그 신고", description = "블로그 신고 시 필요한 API")
    @PostMapping("/report/{blogId}")
    public Response<BlogReportResponseDto> reportBlog(@PathVariable Long blogId, @RequestHeader("UserId") String userId, @RequestBody ReportRequestDto reportRequestDto) {

        return userReportService.reportBlog(blogId, userId, reportRequestDto);
    }

    @Operation(summary = "게시글 신고", description = "게시글 신고 시 필요한 API")
    @PostMapping("/article/report/{articleId}")
    public Response<ArticleReportResponseDto> reportArticle(@PathVariable Long articleId, @RequestHeader("UserId") String userId, @RequestBody ReportRequestDto reportRequestDto) {

        return userReportService.reportArticle(articleId, userId, reportRequestDto);
    }

    @Operation(summary = "댓글 신고", description = "댓글 신고 시 필요한 API")
    @PostMapping("/reply/report/{replyId}")
    public Response<ReplyReportResponseDto> reportReply(@PathVariable Long replyId, @RequestHeader("UserId") String userId, @RequestBody ReportRequestDto reportRequestDto) {
        return userReportService.reportReply(replyId, userId, reportRequestDto);
    }
}
