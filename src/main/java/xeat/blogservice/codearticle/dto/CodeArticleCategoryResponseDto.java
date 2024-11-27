package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.entity.Difficulty;
import xeat.blogservice.global.ResponseDto;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticleCategoryResponseDto implements ResponseDto {

    private Long articleId;

    private Long blogId;

    private String childName;

    private String title;

    private String content;

    private String thumbnailImageUrl;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private Integer codeId;

    private LocalDateTime createdDate;

    public static CodeArticleCategoryResponseDto toDto(CodeArticle codeArticle) {
        return new CodeArticleCategoryResponseDto(
                codeArticle.getArticle().getId(),
                codeArticle.getArticle().getBlog().getId(),
                codeArticle.getArticle().getChildCategory().getChildName(),
                codeArticle.getArticle().getTitle(),
                Base64.getEncoder().encodeToString(codeArticle.getArticle().getContent().getBytes()),
                codeArticle.getArticle().getThumbnailImageUrl(),
                codeArticle.getArticle().getLikeCount(),
                codeArticle.getArticle().getReplyCount(),
                codeArticle.getArticle().getViewCount(),
                codeArticle.getCodeId(),
                codeArticle.getArticle().getCreatedDate()
        );
    }
}
