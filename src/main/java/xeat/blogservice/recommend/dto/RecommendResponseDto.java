package xeat.blogservice.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendResponseDto {

    public Integer likeCount;

    public static RecommendResponseDto toDto(Article article) {
        return new RecommendResponseDto(article.getLikeCount());
    }
}
