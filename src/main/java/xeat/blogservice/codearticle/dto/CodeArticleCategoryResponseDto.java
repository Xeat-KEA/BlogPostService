package xeat.blogservice.codearticle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.data.elasticsearch.core.SearchHit;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.global.response.ResponseDto;
import xeat.blogservice.search.entity.ElasticArticle;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeArticleCategoryResponseDto implements ResponseDto {

    private Long articleId;

    private String childName;

    private Boolean isSecret;

    private Boolean isBlind;

    private String title;

    private String content;

    private String thumbnailImageUrl;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private Integer codeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static CodeArticleCategoryResponseDto toDto(CodeArticle codeArticle, String content) {
        return new CodeArticleCategoryResponseDto(
                codeArticle.getArticle().getId(),
                codeArticle.getArticle().getChildCategory().getChildName(),
                codeArticle.getArticle().getIsSecret(),
                codeArticle.getArticle().getIsBlind(),
                codeArticle.getArticle().getTitle(),
                Base64.getEncoder().encodeToString(content.getBytes()),
                codeArticle.getArticle().getThumbnailImageUrl(),
                codeArticle.getArticle().getLikeCount(),
                codeArticle.getArticle().getReplyCount(),
                codeArticle.getArticle().getViewCount(),
                codeArticle.getCodeId(),
                codeArticle.getArticle().getCreatedDate()
        );
    }
}
