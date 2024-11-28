package xeat.blogservice.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.follow.entity.Follow;
import xeat.blogservice.global.FullTimeEntity;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.parentcategory.entity.ParentCategory;
import xeat.blogservice.recommend.entity.Recommend;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.report.entity.UserReport;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BlOG")
public class Blog extends FullTimeEntity {

    @PrePersist
    public void prePersist() {

        this.noticeCheck = this.noticeCheck == null || this.noticeCheck;
        this.followCount = (this.followCount == null) ? 0 : this.followCount;

    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BLOG_ID")
    private Long id;

    @Column(name = "USER_ID", unique = true)
    private String userId;

    @Column(name = "MAIN_CONTENT", columnDefinition = "TEXT")
    private String mainContent;

    @Column(name = "FOLLOW_COUNT", columnDefinition = "INTEGER")
    private Integer followCount;

    @Column(name = "NOTICE_CHECK", columnDefinition = "BOOLEAN")
    private Boolean noticeCheck;

    @Builder.Default
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "sentUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> sentUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParentCategory> parentCategories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommend> users = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> targetUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "followUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "mentionedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> mentionedUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reportUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> userReports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> blogs = new ArrayList<>();


    public void plusFollowCount() {
        this.followCount += 1;
    }

    public void minusFollowCount() {
        this.followCount -= 1;
    }

    public void updateMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public void updateNoticeCheckTrue() {
        this.noticeCheck = true;
    }

    public void updateNoticeCheckFalse() {
        this.noticeCheck = false;
    }
}
