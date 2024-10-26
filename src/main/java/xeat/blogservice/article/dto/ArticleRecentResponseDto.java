package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.global.ResponseDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRecentResponseDto implements ResponseDto {

    private Long articleId;

    private Long blogId;

    private String title;

    private String content;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private LocalDateTime createdDate;

    public static ArticleRecentResponseDto toDto(Article article) {
        return new ArticleRecentResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getTitle(),
                article.getContent(),
                article.getLikeCount(),
                article.getReplyCount(),
                article.getViewCount(),
                article.getCreatedDate()
        );
    }
}
