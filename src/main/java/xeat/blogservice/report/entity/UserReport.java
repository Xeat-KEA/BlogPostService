package xeat.blogservice.report.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.CreatedTimeEntity;
import xeat.blogservice.reply.entity.Reply;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER_REPORT")
public class UserReport extends CreatedTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_REPORT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REPORTER_ID", referencedColumnName = "USER_ID")
    private Blog reportUser;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "REPLY_ID")
    private Reply reply;

    @Column(name = "REPORT_CATEGORY")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportCategory reportCategory;

    @Column(name = "DIRECT_CATEGORY")
    private String directCategory;
}
