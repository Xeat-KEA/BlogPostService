package xeat.blogservice.search.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xeat.blogservice.search.entity.ElasticArticle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ArticleSearchResultDto {
    private String nickname;
    private String profileUrl;
    private Integer articleId;
    private Integer codeId;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;

    public ArticleSearchResultDto(ElasticArticle content, Map<String, List<String>> highlightFields) {
        this.nickname = content.getNickname();
        this.profileUrl = content.getProfileUrl();
        this.articleId = content.getArticleId();
        this.codeId = content.getCodeId();
        this.title = content.getTitle();
        StringBuilder stringBuilder = new StringBuilder();
        if (highlightFields.containsKey("content")) {
            for (String string : highlightFields.get("content")) {
                stringBuilder.append(string).append("..");
            }
            if (stringBuilder.length() > 160) {
                this.content = stringBuilder.substring(0, 170)
                        .replaceAll("(?i)<(?!/?b(?=>|\\s.*>))[^>]*>", "").substring(0, 160);
            } else {
                this.content = stringBuilder.toString().replaceAll("(?i)<(?!/?b(?=>|\\s.*>))[^>]*>", "");
            }
        } else {
            if (content.getContent().length() > 160) {
                this.content = content.getContent().substring(0, 170).replaceAll("<[^>]*>", "").substring(0, 160);
            } else {
                this.content = content.getContent().replaceAll("<[^>]*>", "");
            }

        }

        this.createdDate = content.getCreatedDate();
        this.likeCount = content.getLikeCount();
        this.commentCount = content.getCommentCount();
        this.viewCount = content.getViewCount();
    }
}
