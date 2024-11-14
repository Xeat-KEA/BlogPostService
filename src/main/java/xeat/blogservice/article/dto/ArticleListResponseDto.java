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
public class ArticleListResponseDto implements ResponseDto {

    private Long articleId;

    private Boolean isSecret;

    private String nickName;

    private String title;

    private String content;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private LocalDateTime createdDate;

    public static ArticleListResponseDto toDto(Article article, String nickName) {
        return new ArticleListResponseDto(
                article.getId(),
                article.getIsSecret(),
                nickName,
                article.getTitle(),
                article.getContent(),
                article.getLikeCount(),
                article.getReplyCount(),
                article.getViewCount(),
                article.getCreatedDate()
        );
    }
}
