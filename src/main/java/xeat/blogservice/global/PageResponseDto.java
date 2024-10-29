package xeat.blogservice.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import xeat.blogservice.announce.entity.Announce;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.CodeArticle;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto {

    private Integer currentPageNum;

    private Integer totalPageNum;

    public static PageResponseDto announceDto(Page<Announce> announcePageList) {
        return new PageResponseDto(
                announcePageList.getNumber() + 1,
                announcePageList.getTotalPages()
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
}
