package xeat.blogservice.codearticle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.BaseTimeEntity;
import xeat.blogservice.article.entity.Article;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CODE_ARTICLE")
public class CodeArticle extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CODE_ARTICLE_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ARTICLE_ID")
    private Article article;

    @Column(name = "DIFFICULTY")
    @NotNull
    private Difficulty difficulty;

    @Column(name = "CODE_ID")
    @NotNull
    private long codeId;

    @Column(name = "CODE_CONTENT")
    @NotNull
    private String codeContent;

    @Column(name = "WRITTEN_CODE")
    @NotNull
    private String writtenCode;
}
