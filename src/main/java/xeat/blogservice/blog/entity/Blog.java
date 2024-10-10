package xeat.blogservice.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import xeat.blogservice.global.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "BlOG")
@DynamicInsert
public class Blog extends BaseTimeEntity {


    @PrePersist
    public void prePersist() {

        this.noticeCheck = this.noticeCheck == null || this.noticeCheck;
        this.followCount = (this.followCount == null) ? 0 : this.followCount;

    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BLOG_ID")
    @NotNull
    private long id;

    @Column(name = "USER_ID", unique = true)
    @NotNull
    private long userId;

    @Column(name = "INTRODUCE", columnDefinition = "VARCHAR(50)")
    private String introduce;

    @Column(name = "MAIN_CONTENT", columnDefinition = "TEXT")
    private String mainContent;

    @Column(name = "FOLLOW_COUNT", columnDefinition = "INTEGER")
    private Integer followCount;

    @Column(name = "NOTICE_CHECK", columnDefinition = "BOOLEAN")
    private Boolean noticeCheck;
}
