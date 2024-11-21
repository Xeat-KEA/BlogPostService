package xeat.blogservice.codearticle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.codearticle.dto.CodeArticleEditRequestDto;
import xeat.blogservice.global.CreatedTimeEntity;
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
    private String codeId;

    @Column(name = "CODE_CONTENT", columnDefinition = "TEXT")
    private String codeContent;

    @Column(name = "WRITTEN_CODE", columnDefinition = "TEXT")
    private String writtenCode;

    public void editCodeArticle(CodeArticleEditRequestDto codeArticleEditRequestDto) {
        this.codeContent = codeArticleEditRequestDto.getCodeContent();
        this.writtenCode = codeArticleEditRequestDto.getWrittenCode();
    }
}
