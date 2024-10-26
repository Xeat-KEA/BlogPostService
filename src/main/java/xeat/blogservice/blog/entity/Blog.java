package xeat.blogservice.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import xeat.blogservice.global.CreatedTimeEntity;
import xeat.blogservice.global.FullTimeEntity;

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
    @NotNull
    private Long userId;

    @Column(name = "MAIN_CONTENT", columnDefinition = "TEXT")
    private String mainContent;

    @Column(name = "FOLLOW_COUNT", columnDefinition = "INTEGER")
    private Integer followCount;

    @Column(name = "NOTICE_CHECK", columnDefinition = "BOOLEAN")
    private Boolean noticeCheck;

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
