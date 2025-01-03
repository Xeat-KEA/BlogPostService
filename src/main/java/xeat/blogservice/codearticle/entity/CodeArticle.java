package xeat.blogservice.codearticle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.time.CreatedTimeEntity;
import xeat.blogservice.article.entity.Article;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CODE_ARTICLE")
public class CodeArticle extends CreatedTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CODE_ARTICLE_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ARTICLE_ID")
    private Article article;

    @Column(name = "CODE_ID")
    private Integer codeId;

    @Column(name = "WRITTEN_CODE", columnDefinition = "LONGTEXT")
    private String writtenCode;

}
