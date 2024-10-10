package xeat.blogservice.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xeat.blogservice.article.dto.ArticlePostRequestDto;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.global.Response;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final BlogRepository blogRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final ArticleRepository articleRepository;

    public Response<Article> post(ArticlePostRequestDto articlePostRequestDto) {

        Article article = Article.builder()
                .blog(blogRepository.findById(articlePostRequestDto.getBlogId()).get())
                .childCategory(childCategoryRepository.findById(articlePostRequestDto.getChildCategoryId()).get())
                .title(articlePostRequestDto.getTitle())
                .content(articlePostRequestDto.getContent())
                .isSecret(articlePostRequestDto.getIsSecret())
                .isBlind(articlePostRequestDto.getIsBlind())
                .password(articlePostRequestDto.getPassword())
                .build();
        return Response.success(articleRepository.save(article));
    }
}
