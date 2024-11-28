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
        for (String string : highlightFields.get("content")) {
            stringBuilder.append(string).append("..");
        }
        this.content = stringBuilder.toString().replaceAll("(?i)<(?!/?b(?=>|\\s.*>))[^>]*>", "");
        this.createdDate = content.getCreatedDate();
        this.likeCount = content.getLikeCount();
        this.commentCount = content.getCommentCount();
        this.viewCount = content.getViewCount();
    }
}