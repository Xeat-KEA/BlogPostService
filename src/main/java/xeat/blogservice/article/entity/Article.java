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
import xeat.blogservice.global.FullTimeEntity;
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

    @Builder.Default
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Recommend> recommends = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<UserReport> userReports = new ArrayList<>();

    public void editArticle(ArticleEditRequestDto articleEditRequestDto, ChildCategory childCategory) {
        this.childCategory = childCategory;
        this.title = articleEditRequestDto.getTitle();
        this.content = articleEditRequestDto.getContent();
        this.isSecret = articleEditRequestDto.getIsSecret();
        this.password = articleEditRequestDto.getPassword();
    }

    public void editCodeArticle(CodeArticleEditRequestDto codeArticleEditRequestDto) {
        this.title = codeArticleEditRequestDto.getTitle();
        this.content = codeArticleEditRequestDto.getContent();
        this.isSecret = codeArticleEditRequestDto.getIsSecret();
        this.password = codeArticleEditRequestDto.getPassword();
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

    public void minusReportCount() {
        this.reportCount -= 1;
    }

    public void updateIsBlindTrue(Boolean blindTrue) {
        this.isBlind = blindTrue;
    }

    public void updateIsBlindFalse(Boolean blindFalse) {
        this.isBlind = blindFalse;
    }

}
