package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.reply.dto.ArticleReplyResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleResponseDto {

    private Long articleId;
    private Long blogId;
    private Long childCategoryId;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private Integer reportCount;
    private Integer replyCount;
    private List<ArticleReplyResponseDto> articleReplies;

    public static GetArticleResponseDto toDto(Article article, List<ArticleReplyResponseDto> articleReplies) {
        return new GetArticleResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getChildCategory().getId(),
                article.getTitle(),
                article.getContent(),
                article.getViewCount(),
                article.getLikeCount(),
                article.getReportCount(),
                article.getReplyCount(),
                articleReplies
        );
    }
}