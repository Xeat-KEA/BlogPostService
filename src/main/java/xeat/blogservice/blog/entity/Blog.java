package xeat.blogservice.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "BlOG")
public class Blog extends BaseTimeEntity {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BLOG_ID")
    @NotNull
    private long id;

    @Column(name = "USER_ID", unique = true)
    @NotNull
    private long userId;

    @Column(name = "INTRODUCE")
    @NotNull
    private String introduce;

    @Column(name = "MAIN_CONTENT")
    @NotNull
    private String mainContent;

    @Column(name = "FOLLOW_COUNT")
    @NotNull
    private String followCount;

    @Column(name = "NOTICE_CHECK")
    @NotNull
    private Boolean noticeCheck;
}
