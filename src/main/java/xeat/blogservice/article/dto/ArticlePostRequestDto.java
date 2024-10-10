package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePostRequestDto {
    private Long blogId;

    private Long childCategoryId;

    private String title;

    private String content;

    private Boolean isSecret;

    private Boolean isBlind;

    private String password;
}
