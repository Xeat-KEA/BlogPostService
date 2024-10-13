package xeat.blogservice.report.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.global.Response;
import xeat.blogservice.report.dto.ArticleReportRequestDto;
import xeat.blogservice.report.dto.ArticleReportResponseDto;
import xeat.blogservice.report.dto.BlogReportRequestDto;
import xeat.blogservice.report.dto.BlogReportResponseDto;
import xeat.blogservice.report.service.UserReportService;

@RestController
@RequiredArgsConstructor
public class UserReportController {

    private final UserReportService userReportService;

    @PostMapping("/blog/board/report/{blogId}")
    public Response<BlogReportResponseDto> reportBlog(@PathVariable Long blogId, @RequestBody BlogReportRequestDto blogReportRequestDto) {

        return userReportService.reportBlog(blogId, blogReportRequestDto);
    }

    @PostMapping("/blog/board/article/report/{articleId}")
    public Response<ArticleReportResponseDto> reportArticle(@PathVariable Long articleId, @RequestBody ArticleReportRequestDto articleReportRequestDto) {

        return userReportService.reportArticle(articleId, articleReportRequestDto);
    }
}
