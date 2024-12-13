package xeat.blogservice.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchPage;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.follow.entity.Follow;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.recommend.entity.Recommend;
import xeat.blogservice.report.entity.UserReport;
import xeat.blogservice.search.entity.ElasticArticle;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto {

    private Integer currentPageNum;

    private Integer totalPageNum;

    public static PageResponseDto elasticDto(SearchPage<ElasticArticle> elasticArticlePageList) {
        return new PageResponseDto(
                elasticArticlePageList.getNumber() + 1,
                elasticArticlePageList.getTotalPages()
        );
    }

    public static PageResponseDto articleDto(Page<Article> articlePageList) {
        return new PageResponseDto(
                articlePageList.getNumber() + 1,
                articlePageList.getTotalPages()
        );
    }

    public static PageResponseDto codeArticleDto(Page<CodeArticle> codeArticlePageList) {
        return new PageResponseDto(
                codeArticlePageList.getNumber() + 1,
                codeArticlePageList.getTotalPages()
        );
    }

    public static PageResponseDto userReportDto(Page<UserReport> userReportPageList) {
        return new PageResponseDto(
                userReportPageList.getNumber() + 1,
                userReportPageList.getTotalPages()
        );
    }

    public static PageResponseDto noticeDto(Page<Notice> noticePageList) {
        return new PageResponseDto(
                noticePageList.getNumber() + 1,
                noticePageList.getTotalPages()
        );
    }

    public static PageResponseDto followDto(Page<Follow> followPageList) {
        return new PageResponseDto(
                followPageList.getNumber() + 1,
                followPageList.getTotalPages()
        );
    }

    public static PageResponseDto recommendDto(Page<Recommend> recommendPageList) {
        return new PageResponseDto(
                recommendPageList.getNumber() + 1,
                recommendPageList.getTotalPages()
        );
    }
}
