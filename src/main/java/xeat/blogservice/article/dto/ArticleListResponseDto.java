package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListResponseDto implements ResponseDto {

    private Long articleId;

    private Long blogId;

    private Boolean isSecret;

    private String nickName;

    private String profileUrl;

    private String title;

    private String content;

    private String thumbnailImageUrl;

    private Integer likeCount;

    private Integer replyCount;

    private Integer viewCount;

    private LocalDateTime createdDate;

    public static ArticleListResponseDto toDto(Article article, UserInfoResponseDto userInfo) {
        return new ArticleListResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getIsSecret(),
                userInfo.getNickName(),
                userInfo.getProfileUrl(),
                article.getTitle(),
                Base64.getEncoder().encodeToString(article.getContent().getBytes()),
                article.getThumbnailImageUrl(),
                article.getLikeCount(),
                article.getReplyCount(),
                article.getViewCount(),
                article.getCreatedDate()
        );
    }
}
