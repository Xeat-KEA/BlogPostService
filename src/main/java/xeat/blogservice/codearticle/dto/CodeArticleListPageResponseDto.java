package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.PageResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticleListPageResponseDto {

    private PageResponseDto pageInfo;

    private List<CodeArticleListResponseDto> articleList;

    public static CodeArticleListPageResponseDto toDto(PageResponseDto pageInfo, List<CodeArticleListResponseDto> articleList) {
        return new CodeArticleListPageResponseDto(
                pageInfo,
                articleList
        );
    }
}
