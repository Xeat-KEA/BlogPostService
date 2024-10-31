package xeat.blogservice.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.ResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 목록 페이징 처리 응답 DTO")
public class ArticleListPageResponseDto {

    private PageResponseDto pageInfo;

    private List<ResponseDto> responseDtoList;

    public static ArticleListPageResponseDto toDto(PageResponseDto pageInfo, List<ResponseDto> responseDtoList) {
        return new ArticleListPageResponseDto(
                pageInfo,
                responseDtoList
        );
    }
}
