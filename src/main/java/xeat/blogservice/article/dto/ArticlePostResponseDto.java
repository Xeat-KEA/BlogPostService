package xeat.blogservice.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePostResponseDto {

    private Long articleId;
    private Long blogId;
    private String childName;
    private String title;
    private String content;
    private String thumbnailImageUrl;
    private Boolean isSecret;
    private Boolean isBlind;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public static ArticlePostResponseDto toDto(Article article) {
        return new ArticlePostResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getChildCategory().getChildName(),
                article.getTitle(),
                Base64.getEncoder().encodeToString(article.getContent().getBytes()),
                article.getThumbnailImageUrl(),
                article.getIsSecret(),
                article.getIsBlind(),
                article.getCreatedDate()
        );
    }
}
