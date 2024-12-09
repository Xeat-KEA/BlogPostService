package xeat.blogservice.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.response.PageResponseDto;
import xeat.blogservice.global.response.ResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 목록 페이징 처리 응답 DTO")
public class ArticleListPageResponseDto {

    private PageResponseDto pageInfo;

    private Long blogId;

    private List<ResponseDto> articleList;

    public static ArticleListPageResponseDto toDto(PageResponseDto pageInfo, Long blogId, List<ResponseDto> articleList) {
        return new ArticleListPageResponseDto(
                pageInfo,
                blogId,
                articleList
        );
    }
}
