package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.entity.Difficulty;
import xeat.blogservice.reply.dto.ArticleReplyResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCodeArticleResponseDto {
    private Long articleId;
    private Long blogId;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private Integer reportCount;
    private Integer replyCount;

    // 코딩 테스트 게시글 정보
    private Difficulty difficulty;
    private String codeId;
    private String codeContent;
    private String writtenCode;


    private List<ArticleReplyResponseDto> articleReplies;

    public static GetCodeArticleResponseDto toDto(Article article, CodeArticle codeArticle, List<ArticleReplyResponseDto> articleReplies) {
        return new GetCodeArticleResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getTitle(),
                article.getContent(),
                article.getViewCount(),
                article.getLikeCount(),
                article.getReportCount(),
                article.getReplyCount(),
                codeArticle.getDifficulty(),
                codeArticle.getCodeId(),
                codeArticle.getCodeContent(),
                codeArticle.getWrittenCode(),
                articleReplies
        );
    }
}
