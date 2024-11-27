package xeat.blogservice.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.reply.dto.ArticleReplyResponseDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원용 게시글 상세 조회 응답 DTO")
public class GetArticleResponseLoginDto implements ResponseDto {

    @Schema(description = "게시글 고유 ID", example = "1")
    private Long articleId;

    @Schema(description = "해당 게시글이 위치하는 블로그 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "게시글이 위치해 있는 하위 게시판 고유 id", example = "1")
    private Long childCategoryId;

    @Schema(description = "게시글이 위치해있는 하위게시판 이름", example = "하위게시판1")
    private String childName;

    @Schema(description = "게시글 작성자 이름", example = "감만세")
    private String userName;

    @Schema(description = "게시글 작성자 프로필 이미지 URL", example = "http://172.16.211.172/uploadBucket/{이미지 이름}")
    private String profileUrl;

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

    @Schema(description = "게시글 좋아요 눌렀는지 여부", example = "true")
    private Boolean checkRecommend;

    @Schema(description = "게시글 비밀글 여부", example = "true")
    private Boolean isSecret;

    @Schema(description = "게시글 블라인드 여부", example = "true")
    private Boolean isBlind;

    @Schema(description = "게시글 생성 일자", example = "2024-10-17T12:26:17.551429")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Schema(description = "게시글에 달린 댓글 목록", example = "[" +
            "{\"replyId\": 1, \"blogId\": \"1\", \"userName\": \"감만세\", \"profileUrl\": \"{해당 사용자 profileURl}\", \"content\": \"댓글1\", \"createdDate\": \"2024-10-22T18:31:33.2728\", \"childReplies\": [" +
            "{\"replyId\": 2, \"blogId\": \"2\", \"userName\": \"박정재\", \"profileUrl\": \"{해당 사용자 profileURl}\", \"parentReplyId\": 1, \"mentionedUserName\": \"김만세\", \"content\": \"대댓글1\", \"createdDate\": \"2024-10-22T18:32:22.863803\"}" +
            "]}" +
            "]")
    private List<ArticleReplyResponseDto> articleReplies;


    public static GetArticleResponseLoginDto toDto(Article article, UserInfoResponseDto userInfo, List<ArticleReplyResponseDto> articleReplies, Boolean checkRecommend) {
        return new GetArticleResponseLoginDto(
                article.getId(),
                article.getBlog().getId(),
                article.getChildCategory().getId(),
                article.getChildCategory().getChildName(),
                userInfo.getNickName(),
                userInfo.getProfileUrl(),
                article.getTitle(),
                Base64.getEncoder().encodeToString(article.getContent().getBytes()),
                article.getViewCount(),
                article.getLikeCount(),
                article.getReplyCount(),
                checkRecommend,
                article.getIsSecret(),
                article.getIsBlind(),
                article.getCreatedDate(),
                articleReplies
        );
    }
}