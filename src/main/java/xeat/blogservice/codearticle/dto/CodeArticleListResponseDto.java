package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.codearticle.entity.CodeArticle;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticleListResponseDto implements ResponseDto {

    private Long articleId;

    private Long blogId;

    private Boolean isSecret;

    private String nickName;

    private String title;

    private String content;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private String codeId;

    private LocalDateTime createdDate;

    public static CodeArticleListResponseDto toDto(CodeArticle codeArticle, String nickName) {
        return new CodeArticleListResponseDto(
                codeArticle.getArticle().getId(),
                codeArticle.getArticle().getBlog().getId(),
                codeArticle.getArticle().getIsSecret(),
                nickName,
                codeArticle.getArticle().getTitle(),
                Base64.getEncoder().encodeToString(codeArticle.getArticle().getContent().getBytes()),
                codeArticle.getArticle().getLikeCount(),
                codeArticle.getArticle().getReplyCount(),
                codeArticle.getArticle().getViewCount(),
                codeArticle.getCodeId(),
                codeArticle.getArticle().getCreatedDate()
        );
    }
}
