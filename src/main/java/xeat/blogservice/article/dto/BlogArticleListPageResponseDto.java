package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.ResponseDto;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogArticleListPageResponseDto {

    private PageResponseDto pageInfo;

    private Long blogId;

    private List<ResponseDto> articleList;

    public static BlogArticleListPageResponseDto toDto(PageResponseDto pageInfo, Long blogId, List<ResponseDto> articleList) {
        return new BlogArticleListPageResponseDto(
                pageInfo,
                blogId,
                articleList
        );
    }
}
