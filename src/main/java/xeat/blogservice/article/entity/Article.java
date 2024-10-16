package xeat.blogservice.article.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.global.FullTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ARTICLE")
@Builder(toBuilder = true)
public class Article extends FullTimeEntity {

    @PrePersist
    public void prePersist() {
        this.likeCount = (this.likeCount == null) ? 0 : this.likeCount;
        this.replyCount = (this.replyCount == null) ? 0 : this.replyCount;
        this.reportCount = (this.reportCount == null) ? 0 : this.reportCount;
        this.viewCount = (this.viewCount == null) ? 0 : this.viewCount;
        this.isBlind = (this.isBlind == null) ? false : this.isBlind;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="BLOG_ID", referencedColumnName = "BLOG_ID")
    @NotNull
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "CHILD_CATEGORY_ID")
    private ChildCategory childCategory;

    @Column(name = "TITLE", columnDefinition = "VARCHAR(20)")
    @NotNull
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Column(name = "VIEW_COUNT")
    @NotNull
    private Integer viewCount;

    @Column(name = "IS_SECRET", columnDefinition = "BOOLEAN")
    private Boolean isSecret;

    @Column(name = "IS_BLIND", columnDefinition = "BOOLEAN")
    private Boolean isBlind;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "LIKE_COUNT")
    @NotNull
    private Integer likeCount;

    @Column(name = "REPLY_COUNT")
    @NotNull
    private Integer replyCount;

    @Column(name = "REPORT_COUNT")
    @NotNull
    private Integer reportCount;
}
