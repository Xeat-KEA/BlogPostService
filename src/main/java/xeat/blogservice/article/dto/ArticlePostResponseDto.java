package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePostResponseDto {

    private Long articleId;
    private Long blogId;
    private String childName;
    private String title;
    private String content;
    private Boolean isSecret;
    private LocalDateTime createdDate;

    public static ArticlePostResponseDto toDto(Article article) {
        return new ArticlePostResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getChildCategory().getChildName(),
                article.getTitle(),
                article.getContent(),
                article.getIsSecret(),
                article.getCreatedDate()
        );
    }
}
