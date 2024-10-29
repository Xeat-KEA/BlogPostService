package xeat.blogservice.codearticle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.codearticle.dto.*;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.Response;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeArticleService {

    private final BlogRepository blogRepository;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;
    private final ChildCategoryRepository childCategoryRepository;

    @Transactional
    public Response<CodeArticleListPageResponseDto> getTop5RecentCodeArticle(int page, int size) {
        Page<CodeArticle> codeArticlePage = codeArticleRepository.findCodeArticleRecent(PageRequest.of(page, size));
        PageResponseDto pageInfo = PageResponseDto.codeArticleDto(codeArticlePage);

        List<CodeArticleListResponseDto> recentCodeArticleListDto = new ArrayList<>();

        codeArticlePage.getContent().forEach(s -> recentCodeArticleListDto.add(CodeArticleListResponseDto.toDto(s)));
        return Response.success(CodeArticleListPageResponseDto.toDto(pageInfo, recentCodeArticleListDto));
    }

    @Transactional
    public Response<CodeArticleResponseDto> post(CodeArticlePostRequestDto codeArticlePostRequestDto) {
        Article article = Article.builder()
                .blog(blogRepository.findById(codeArticlePostRequestDto.getBlogId()).get())
                .childCategory(childCategoryRepository.findById(codeArticlePostRequestDto.getChildCategoryId()).get())
                .title(codeArticlePostRequestDto.getTitle())
                .content(codeArticlePostRequestDto.getContent())
                .isSecret(codeArticlePostRequestDto.getIsSecret())
                .password(codeArticlePostRequestDto.getPassword())
                .build();
        articleRepository.save(article);

        CodeArticle codeArticle = CodeArticle.builder()
                .article(articleRepository.findById(article.getId()).get())
                .codeId(codeArticlePostRequestDto.getCodeId())
                .codeContent(codeArticlePostRequestDto.getCodeContent())
                .writtenCode(codeArticlePostRequestDto.getWrittenCode())
                .build();

        codeArticleRepository.save(codeArticle);

        return Response.success(CodeArticleResponseDto.toDto(article, codeArticle));
    }

    @Transactional
    public Response<CodeArticleResponseDto> edit(Long articleId, CodeArticleEditRequestDto codeArticleEditRequestDto) {
        CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();

        Article article = articleRepository.findById(codeArticle.getArticle().getId()).get();

        article.editCodeArticle(codeArticleEditRequestDto);
        codeArticle.editCodeArticle(codeArticleEditRequestDto);

        articleRepository.save(article);
        codeArticleRepository.save(codeArticle);

        return Response.success(CodeArticleResponseDto.toDto(article, codeArticle));
    }

}
