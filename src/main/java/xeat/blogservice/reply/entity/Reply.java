package xeat.blogservice.reply.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.time.FullTimeEntity;
import xeat.blogservice.report.entity.UserReport;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REPLY")
public class Reply extends FullTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private Blog user;

    @ManyToOne
    @JoinColumn(name = "MENTIONED_USER_ID", referencedColumnName = "USER_ID")
    private Blog mentionedUser;

    @Column(name = "PARENT_REPLY_ID")
    private Long parentReplyId;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> userReports = new ArrayList<>();


    public void editContent(String content) {
        this.content = content;
    }
}
