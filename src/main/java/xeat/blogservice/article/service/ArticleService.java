package xeat.blogservice.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.dto.ArticleEditRequestDto;
import xeat.blogservice.article.dto.ArticlePostRequestDto;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.Response;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final BlogRepository blogRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;

    @Transactional
    public Response<Article> post(ArticlePostRequestDto articlePostRequestDto) {

        Article article = Article.builder()
                .blog(blogRepository.findById(articlePostRequestDto.getBlogId()).get())
                .childCategory(childCategoryRepository.findById(articlePostRequestDto.getChildCategoryId()).get())
                .title(articlePostRequestDto.getTitle())
                .content(articlePostRequestDto.getContent())
                .isSecret(articlePostRequestDto.getIsSecret())
                .password(articlePostRequestDto.getPassword())
                .build();
        return Response.success(articleRepository.save(article));
    }

    @Transactional
    public Response<Article> edit(Long articleId, ArticleEditRequestDto articleEditRequestDto) {
        Article article = articleRepository.findById(articleId).get();
        ChildCategory childCategory = childCategoryRepository.findById(articleEditRequestDto.getChildCategoryId()).get();

        article.editArticle(articleEditRequestDto, childCategory);
        return Response.success(articleRepository.save(article));
    }

    @Transactional
    public Response<?> delete(Long articleId) {
        articleRepository.deleteById(articleId);

        if (codeArticleRepository.existsByArticleId(articleId)) {
            codeArticleRepository.delete(codeArticleRepository.findByArticleId(articleId).get());
        }
        return new Response<>(200, "게시글 삭제 완료", null);
    }


}
