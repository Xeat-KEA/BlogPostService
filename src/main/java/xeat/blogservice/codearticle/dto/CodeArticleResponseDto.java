package xeat.blogservice.codearticle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.dto.ArticlePostResponseDto;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.entity.Difficulty;
import xeat.blogservice.global.feignclient.CodeBankInfoResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CodeArticleResponseDto extends ArticlePostResponseDto {

    private Long articleId;
    private Long blogId;
    private String childName;
    private String title;
    private String content;
    private String thumbnailImageUrl;
    private Boolean isSecret;
    private Boolean isBlind;
    private String password;

    //코딩테스트 게시글만 별도로 저장
    private Integer codeId;
    private String codeTitle;
    private String codeContent;
    private String writtenCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static CodeArticleResponseDto toDto(CodeArticle codeArticle, CodeBankInfoResponseDto codeBankInfo) {
        return new CodeArticleResponseDto(
                codeArticle.getArticle().getId(),
                codeArticle.getArticle().getBlog().getId(),
                codeArticle.getArticle().getChildCategory().getChildName(),
                codeArticle.getArticle().getTitle(),
                Base64.getEncoder().encodeToString(codeArticle.getArticle().getContent().getBytes()),
                codeArticle.getArticle().getThumbnailImageUrl(),
                codeArticle.getArticle().getIsSecret(),
                codeArticle.getArticle().getIsBlind(),
                codeArticle.getArticle().getPassword(),
                codeArticle.getCodeId(),
                codeBankInfo.getTitle(),
                codeBankInfo.getContent(),
                codeArticle.getWrittenCode(),
                codeArticle.getArticle().getCreatedDate()
        );
    }
}
