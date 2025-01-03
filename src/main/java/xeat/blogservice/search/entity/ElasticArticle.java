package xeat.blogservice.search.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "article")
@Mapping(mappingPath = "elastic/article-mapping.json")
@Setting(settingPath = "elastic/article-setting.json")
@Getter
public class ElasticArticle {
    @Id
    private Integer id;
    @Field(name="nick_name")
    private String nickname;
    @Field(name="profile_url")
    private String profileUrl;
    @Field(name="article_id")
    private Integer articleId;
    @Field(name="code_id")
    private Integer codeId;
    @Field(name="title")
    private String title;
    @Field(name="content")
    private String content;
    @Field(name="created_date")
    private LocalDateTime createdDate;
    @Field(name="like_count")
    private Integer likeCount;
    @Field(name="reply_count")
    private Integer commentCount;
    @Field(name="view_count")
    private Integer viewCount;
    @Field(name="child_category_id")
    private Long child_category_id;
    @Field(name="parent_category_id")
    private Long parent_category_id;
    @Field(name="blog_id")
    private Long blog_id;

    public ElasticArticle() {

    }
}
