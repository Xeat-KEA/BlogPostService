package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.response.PageResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticleListPageResponseDto {

    private PageResponseDto pageInfo;

    private Long totalArticle;

    private List<CodeArticleListResponseDto> articleList;

    public static CodeArticleListPageResponseDto toDto(PageResponseDto pageInfo, Long totalArticle, List<CodeArticleListResponseDto> articleList) {
        return new CodeArticleListPageResponseDto(
                pageInfo,
                totalArticle,
                articleList
        );
    }
}
