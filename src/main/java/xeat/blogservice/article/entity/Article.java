package xeat.blogservice.article.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.dto.ArticleEditRequestDto;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.codearticle.dto.CodeArticleEditRequestDto;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.global.time.FullTimeEntity;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.recommend.entity.Recommend;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.report.entity.UserReport;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ARTICLE")
@Builder
public class Article extends FullTimeEntity {

    @PrePersist
    public void prePersist() {
        this.likeCount = (this.likeCount == null) ? 0 : this.likeCount;
        this.replyCount = (this.replyCount == null) ? 0 : this.replyCount;
        this.reportCount = (this.reportCount == null) ? 0 : this.reportCount;
        this.viewCount = (this.viewCount == null) ? 0 : this.viewCount;
        this.isBlind = this.isBlind != null && this.isBlind;
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

    @Column(name = "THUMBNAIL_IMAGE_URL")
    private String thumbnailImageUrl;

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

    @Column(name = "CODE_ID")
    @NotNull
    private Integer codeId;

    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private CodeArticle codeArticle;

    @Builder.Default
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommend> recommends = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> userReports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices = new ArrayList<>();

    public void editArticle(ArticleEditRequestDto articleEditRequestDto, String password, ChildCategory childCategory, List<String> newUrlAndContent) {
        this.childCategory = childCategory;
        this.title = articleEditRequestDto.getTitle();
        this.content = newUrlAndContent.get(1);
        this.thumbnailImageUrl = newUrlAndContent.get(0);
        this.isSecret = articleEditRequestDto.getIsSecret();
        this.password = password;
    }

    public void editCodeArticle(CodeArticleEditRequestDto codeArticleEditRequestDto, String password, List<String> newUrlAndContent) {
        this.title = codeArticleEditRequestDto.getTitle();
        this.content = newUrlAndContent.get(1);
        this.thumbnailImageUrl = newUrlAndContent.get(0);
        this.isSecret = codeArticleEditRequestDto.getIsSecret();
        this.password = password;
    }

    public void plusLikeCount() {
        this.likeCount += 1;
    }

    public void minusLikeCount() {
        this.likeCount -= 1;
    }

    public void plusReportCount() {
        this.reportCount += 1;
    }

    public void updateIsBlindTrue(Boolean blindTrue) {
        this.isBlind = blindTrue;
    }

    public void updateIsBlindFalse(Boolean blindFalse) {
        this.isBlind = blindFalse;
    }

    public void plusViewCount() {
        this.viewCount += 1;
    }

}
