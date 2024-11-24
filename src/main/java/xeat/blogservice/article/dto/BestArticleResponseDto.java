package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.ResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BestArticleResponseDto {

    private List<ResponseDto> articleList;

    public static BestArticleResponseDto toDto(List<ResponseDto> articleList) {
        return new BestArticleResponseDto(
                articleList
        );
    }
}
