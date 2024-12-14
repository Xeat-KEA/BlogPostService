package xeat.blogservice.recommend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.blog.entity.Blog;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RECOMMEND")
public class Recommend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECOMMEND_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID", referencedColumnName="ARTICLE_ID")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private Blog user;

}
