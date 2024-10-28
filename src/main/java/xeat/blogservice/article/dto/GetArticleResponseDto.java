package xeat.blogservice.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.reply.dto.ArticleReplyResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 상세 조회 응답 DTO")
public class GetArticleResponseDto implements ResponseDto {

    @Schema(description = "게시글 고유 ID", example = "1")
    private Long articleId;

    @Schema(description = "게시글 작성자의 블로그 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "게시글이 위치해있는 하위게시판 이름", example = "하위게시판1")
    private String childName;

    @Schema(description = "게시글 제목", example = "제목1")
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용1")
    private String content;

    @Schema(description = "게시글 조회 수", example = "5")
    private Integer viewCount;

    @Schema(description = "게시글 좋아요 수", example = "3")
    private Integer likeCount;

    @Schema(description = "게시글 댓글 수", example = "4")
    private Integer replyCount;

    @Schema(description = "게시글 생성 일자", example = "2024-10-17T12:26:17.551429")
    private LocalDateTime createdDate;

    @Schema(description = "게시글에 달린 댓글 목록", example = "[" +
            "{\"replyId\": 1, \"userId\": 2, \"content\": \"댓글1\", \"createdDate\": \"2024-10-22T18:31:33.2728\", \"childReplies\": [" +
            "{\"replyId\": 2, \"userId\": 2, \"parentReplyId\": 1, \"mentionedUserId\": 1, \"content\": \"대댓글1\", \"createdDate\": \"2024-10-22T18:32:22.863803\"}" +
            "]}" +
            "]")
    private List<ArticleReplyResponseDto> articleReplies;


    public static GetArticleResponseDto toDto(Article article, List<ArticleReplyResponseDto> articleReplies) {
        return new GetArticleResponseDto(
                article.getId(),
                article.getBlog().getId(),
                article.getChildCategory().getChildName(),
                article.getTitle(),
                article.getContent(),
                article.getViewCount(),
                article.getLikeCount(),
                article.getReplyCount(),
                article.getCreatedDate(),
                articleReplies
        );
    }
}