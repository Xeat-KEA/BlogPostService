package xeat.blogservice.article.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.BaseTimeEntity;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.childcategory.entity.ChildCategory;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ARTICLE")
@Builder(toBuilder = true)
public class Article extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="BLOG_ID", referencedColumnName = "BLOG_ID")
    @NotNull
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "CHILD_CATEGORY_ID")
    @NotNull
    private ChildCategory childCategory;

    @Column(name = "TITLE")
    @NotNull
    private String title;

    @Column(name = "CONTENT")
    @NotNull
    private String content;

    @Column(name = "VIEW_COUNT")
    @NotNull
    private int viewCount;

    @Column(name = "IS_SECRET")
    private Boolean isSecret;

    @Column(name = "IS_BLIND")
    private Boolean isBlind;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "LIKE_COUNT")
    @NotNull
    private int likeCount;

    @Column(name = "REPLY_COUNT")
    @NotNull
    private int replyCount;
}
