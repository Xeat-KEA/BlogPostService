package xeat.blogservice.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.report.dto.ArticleReportRequestDto;
import xeat.blogservice.report.dto.ArticleReportResponseDto;
import xeat.blogservice.report.dto.BlogReportRequestDto;
import xeat.blogservice.report.dto.BlogReportResponseDto;
import xeat.blogservice.report.entity.UserReport;
import xeat.blogservice.report.repository.UserReportRepository;

@Service
@RequiredArgsConstructor
public class UserReportService {


    private final UserReportRepository userReportRepository;
    private final BlogRepository blogRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Response<BlogReportResponseDto> reportBlog(Long blogId, BlogReportRequestDto blogReportRequestDto) {

        UserReport userReport = UserReport.builder()
                .blog(blogRepository.findById(blogId).get())
                .reportCategory(blogReportRequestDto.getReportCategory())
                .directCategory(blogReportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);
        return Response.success(BlogReportResponseDto.toDto(userReport));
    }

    @Transactional
    public Response<ArticleReportResponseDto> reportArticle(Long articleId, ArticleReportRequestDto articleReportRequestDto) {

        Article article = articleRepository.findById(articleId).get();

        UserReport userReport = UserReport.builder()
                .article(article)
                .reportCategory(articleReportRequestDto.getReportCategory())
                .directCategory(articleReportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);

        // 게시글 신고 수 +1 and 5개가 될 경우 isBlind = True 처리
        Article updateArticle = article.toBuilder()
                .reportCount(article.getReportCount() + 1)
                .build();
        if (updateArticle.getReportCount() == 5) {
            updateArticle = updateArticle.toBuilder()
                    .isBlind(true)
                    .build();
        }
        articleRepository.save(updateArticle);

        return Response.success(ArticleReportResponseDto.toDto(userReport, article));
    }
}
