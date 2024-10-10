package xeat.blogservice.codearticle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.codearticle.dto.CodeArticlePostRequestDto;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.entity.Difficulty;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.Response;

@Service
@RequiredArgsConstructor
public class CodeArticleService {

    private final BlogRepository blogRepository;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;

    @Transactional
    public Response<CodeArticlePostRequestDto> post(CodeArticlePostRequestDto codeArticlePostRequestDto) {
        Article article = Article.builder()
                .blog(blogRepository.findById(codeArticlePostRequestDto.getBlogId()).get())
                .title(codeArticlePostRequestDto.getTitle())
                .content(codeArticlePostRequestDto.getContent())
                .isSecret(codeArticlePostRequestDto.getIsSecret())
                .isBlind(codeArticlePostRequestDto.getIsBlind())
                .password(codeArticlePostRequestDto.getPassword())
                .build();
        articleRepository.save(article);

        CodeArticle codeArticle = CodeArticle.builder()
                .article(articleRepository.findById(article.getId()).get())
                .difficulty(codeArticlePostRequestDto.getDifficulty())
                .codeId(codeArticlePostRequestDto.getCodeId())
                .codeContent(codeArticlePostRequestDto.getCodeContent())
                .writtenCode(codeArticlePostRequestDto.getWrittenCode())
                .build();

        codeArticleRepository.save(codeArticle);

        return Response.success(CodeArticlePostRequestDto.toDto(article, codeArticle));
    }

}
