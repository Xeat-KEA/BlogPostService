package xeat.blogservice.follow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FOLLOW")
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLLOW_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TARGET_USER_ID", referencedColumnName = "USER_ID")
    private Blog targetUser;

    @ManyToOne
    @JoinColumn(name = "FOLLOW_USER_ID", referencedColumnName = "USER_ID")
    private Blog followUser;
}
