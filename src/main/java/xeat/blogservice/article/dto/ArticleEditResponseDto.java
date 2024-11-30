package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;

import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEditResponseDto {

    private Long articleId;

    private String title;

    private Boolean isBlind;

    private Boolean isSecret;

    private String password;

    private String parentName;

    private String childName;

    private String content;


    public static ArticleEditResponseDto toDto(Article article, String updateContent) {
        return new ArticleEditResponseDto(
                article.getId(),
                article.getTitle(),
                article.getIsBlind(),
                article.getIsSecret(),
                article.getPassword(),
                article.getChildCategory().getParentCategory().getParentName(),
                article.getChildCategory().getChildName(),
                Base64.getEncoder().encodeToString(updateContent.getBytes())
        );
    }
}
