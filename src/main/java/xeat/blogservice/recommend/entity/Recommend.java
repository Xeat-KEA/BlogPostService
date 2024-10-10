package xeat.blogservice.recommend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.BaseTimeEntity;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.blog.entity.Blog;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "RECOMMEND")
public class Recommend extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECOMMEND_ID")
    @NotNull
    private long id;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    @NotNull
    private Article article;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @NotNull
    private Blog user;

}
