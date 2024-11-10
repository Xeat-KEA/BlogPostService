package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.codearticle.entity.CodeArticle;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticleListResponseDto implements ResponseDto {

    private Long articleId;

    private Boolean isSecret;

    private String userId;

    private String title;

    private String content;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private String codeId;

    private LocalDateTime createdDate;

    public static CodeArticleListResponseDto toDto(CodeArticle codeArticle) {
        return new CodeArticleListResponseDto(
                codeArticle.getArticle().getId(),
                codeArticle.getArticle().getIsSecret(),
                codeArticle.getArticle().getBlog().getUserId(),
                codeArticle.getArticle().getTitle(),
                codeArticle.getArticle().getContent(),
                codeArticle.getArticle().getLikeCount(),
                codeArticle.getArticle().getReplyCount(),
                codeArticle.getArticle().getViewCount(),
                codeArticle.getCodeId(),
                codeArticle.getArticle().getCreatedDate()
        );
    }
}
