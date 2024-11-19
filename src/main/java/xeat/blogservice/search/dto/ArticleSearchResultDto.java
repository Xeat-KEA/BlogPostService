package xeat.blogservice.search.dto;

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
        this.content = "";
        for (String string : highlightFields.get("content")) {
            this.content = this.content + " " + string + "..";
        }
        this.createdDate = content.getCreatedDate();
        this.likeCount = content.getLikeCount();
        this.commentCount = content.getCommentCount();
        this.viewCount = content.getViewCount();
    }
}
