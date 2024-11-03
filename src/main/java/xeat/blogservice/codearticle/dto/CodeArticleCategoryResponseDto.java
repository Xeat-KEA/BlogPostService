package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.entity.Difficulty;
import xeat.blogservice.global.ResponseDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticleCategoryResponseDto implements ResponseDto {

    private Long articleId;

    private String childName;

    private String title;

    private String content;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private String codeId;

    private LocalDateTime createdDate;

    public static CodeArticleCategoryResponseDto toDto(CodeArticle codeArticle) {
        return new CodeArticleCategoryResponseDto(
                codeArticle.getArticle().getId(),
                codeArticle.getArticle().getChildCategory().getChildName(),
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