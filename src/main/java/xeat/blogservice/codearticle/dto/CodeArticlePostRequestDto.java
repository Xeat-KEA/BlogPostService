package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.entity.Difficulty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticlePostRequestDto {
    private Long blogId;
    private String title;
    private String content;
    private Boolean isSecret;
    private String password;

    //코딩테스트 게시글만 별도로 저장
    private Difficulty difficulty;
    private Long codeId;
    private String codeContent;
    private String writtenCode;

}
