package xeat.blogservice.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.web.util.HtmlUtils;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.global.response.ResponseDto;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategoryResponseDto implements ResponseDto {

    private Long articleId;

    private Long blogId;

    private String childName;

    private Boolean isSecret;

    private Boolean isBlind;

    private String title;

    private String content;

    private String thumbnailImageUrl;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static ArticleCategoryResponseDto toDto(Article article, String content) {
        return new ArticleCategoryResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getChildCategory().getChildName(),
                article.getIsSecret(),
                article.getIsBlind(),
                article.getTitle(),
                Base64.getEncoder().encodeToString(content.getBytes()),
                article.getThumbnailImageUrl(),
                article.getLikeCount(),
                article.getReplyCount(),
                article.getViewCount(),
                article.getCreatedDate()
        );
    }
}
