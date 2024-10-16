package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEditRequestDto {

    private Long childCategoryId;

    private String title;

    private String content;

    private Boolean isSecret;

    private String password;


}
