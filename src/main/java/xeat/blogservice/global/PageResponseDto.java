package xeat.blogservice.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.CodeArticle;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto {

    private Integer currentPageNum;

    private Integer totalPageNum;

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
