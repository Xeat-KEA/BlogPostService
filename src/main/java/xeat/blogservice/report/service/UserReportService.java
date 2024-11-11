package xeat.blogservice.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;
import xeat.blogservice.notice.repository.NoticeRepository;
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
    private final NoticeRepository noticeRepository;

    @Transactional
    public Response<BlogReportResponseDto> reportBlog(Long blogId, String userId, ReportRequestDto reportRequestDto) {

        UserReport userReport = UserReport.builder()
                .blog(blogRepository.findById(blogId).get())
                .reportUser(blogRepository.findByUserId(userId).get())
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);
        return Response.success(BlogReportResponseDto.toDto(userReport));
    }

    @Transactional
    public Response<ArticleReportResponseDto> reportArticle(Long articleId, String userId, ReportRequestDto reportRequestDto) {

        Article article = articleRepository.findById(articleId).get();

        UserReport userReport = UserReport.builder()
                .article(article)
                .reportUser(blogRepository.findByUserId(userId).get())
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        // 게시글 신고 수 +1 and 5개가 될 경우 isBlind = True 처리
        article.plusReportCount();

        if (article.getReportCount() == 10) {
            // 게시글 블러처리
            article.updateIsBlindTrue(true);

            // 블러처리 알림 등록
            Notice notice = Notice.builder()
                    .blog(article.getBlog())
                    .noticeCategory(NoticeCategory.Blur)
                    .reasonCategory(reportRequestDto.getReportCategory())
                    .content(article.getTitle())
                    .build();
            noticeRepository.save(notice);
        }
        return Response.success(ArticleReportResponseDto.toDto(userReportRepository.save(userReport), articleRepository.save(article)));
    }

    @Transactional
    public Response<ReplyReportResponseDto> reportReply(Long replyId, String userId, ReportRequestDto reportRequestDto) {

        UserReport userReport = UserReport.builder()
                .reply(replyRepository.findById(replyId).get())
                .reportUser(blogRepository.findByUserId(userId).get())
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);
        return Response.success(ReplyReportResponseDto.toDto(userReport));
    }
}
