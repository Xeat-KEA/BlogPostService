package xeat.blogservice.codearticle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.global.response.ResponseDto;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static CodeArticleCategoryResponseDto toDto(CodeArticle codeArticle) {
        return new CodeArticleCategoryResponseDto(
                codeArticle.getArticle().getId(),
                codeArticle.getArticle().getBlog().getId(),
                codeArticle.getArticle().getChildCategory().getChildName(),
                codeArticle.getArticle().getTitle(),
                Base64.getEncoder().encodeToString(codeArticle.getArticle().getContent().replaceAll("<[^>]*>", "").getBytes()),
                codeArticle.getArticle().getThumbnailImageUrl(),
                codeArticle.getArticle().getLikeCount(),
                codeArticle.getArticle().getReplyCount(),
                codeArticle.getArticle().getViewCount(),
                codeArticle.getCodeId(),
                codeArticle.getArticle().getCreatedDate()
        );
    }
}
