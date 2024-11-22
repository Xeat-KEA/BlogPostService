package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.entity.Difficulty;

import java.util.Base64;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CodeArticleResponseDto {

    private Long articleId;
    private Long blogId;
    private String childName;
    private String title;
    private String content;
    private Boolean isSecret;
    private Boolean isBlind;
    private String password;

    //코딩테스트 게시글만 별도로 저장
    private String codeId;
    private String codeContent;
    private String writtenCode;

    public static CodeArticleResponseDto toDto(Article article, CodeArticle codeArticle) {
        return new CodeArticleResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getChildCategory().getChildName(),
                article.getTitle(),
                Base64.getEncoder().encodeToString(codeArticle.getArticle().getContent().getBytes()),
                article.getIsSecret(),
                article.getIsBlind(),
                article.getPassword(),

                codeArticle.getCodeId(),
                codeArticle.getCodeContent(),
                codeArticle.getWrittenCode()
        );
    }
}
