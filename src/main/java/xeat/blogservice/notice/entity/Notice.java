package xeat.blogservice.notice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.CreatedTimeEntity;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.report.entity.ReportCategory;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "NOTICE")
public class Notice extends CreatedTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "BLOG_ID")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private Blog sentUser;

    @Column(name = "NOTICE_CATEGORY")
    @Enumerated(EnumType.STRING)
    @NotNull
    private NoticeCategory noticeCategory;

    @Column(name = "REASON_CATEGORY")
    @Enumerated(EnumType.STRING)
    private ReportCategory reasonCategory;

    @Column(name = "CONTENT")
    private String content;
}
