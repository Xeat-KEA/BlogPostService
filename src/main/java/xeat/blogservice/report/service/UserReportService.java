package xeat.blogservice.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.reply.repository.ReplyRepository;
import xeat.blogservice.report.dto.*;
import xeat.blogservice.report.entity.UserReport;
import xeat.blogservice.report.repository.UserReportRepository;

@Service
@RequiredArgsConstructor
public class UserReportService {


    private final UserReportRepository userReportRepository;
    private final BlogRepository blogRepository;
    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Response<BlogReportResponseDto> reportBlog(Long blogId, ReportRequestDto reportRequestDto) {

        UserReport userReport = UserReport.builder()
                .blog(blogRepository.findById(blogId).get())
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);
        return Response.success(BlogReportResponseDto.toDto(userReport));
    }

    @Transactional
    public Response<ArticleReportResponseDto> reportArticle(Long articleId, ReportRequestDto reportRequestDto) {

        Article article = articleRepository.findById(articleId).get();

        UserReport userReport = UserReport.builder()
                .article(article)
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);

        // 게시글 신고 수 +1 and 5개가 될 경우 isBlind = True 처리
        article.plusReportCount();
        if (article.getReportCount() == 5) {
            article.updateIsBlindTrue(true);
        }
        articleRepository.save(article);

        return Response.success(ArticleReportResponseDto.toDto(userReport, article));
    }

    @Transactional
    public Response<ReplyReportResponseDto> reportReply(Long replyId, ReportRequestDto reportRequestDto) {

        UserReport userReport = UserReport.builder()
                .reply(replyRepository.findById(replyId).get())
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);
        return Response.success(ReplyReportResponseDto.toDto(userReport));
    }
}
