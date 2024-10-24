package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.codearticle.entity.CodeArticle;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticleRecentResponseDto {

    private Long articleId;

    private Long userId;

    private String title;

    private String content;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private String codeId;

    private LocalDateTime createdDate;

    public static CodeArticleRecentResponseDto toDto(CodeArticle codeArticle) {
        return new CodeArticleRecentResponseDto(
                codeArticle.getArticle().getId(),
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
