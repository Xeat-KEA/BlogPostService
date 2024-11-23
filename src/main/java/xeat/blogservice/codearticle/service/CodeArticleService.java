package xeat.blogservice.codearticle.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
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
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.image.service.ImageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeArticleService {

    private final BlogRepository blogRepository;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final UserFeignClient userFeignClient;
    private final ImageService minioImageService;

    @Transactional
    public Response<CodeArticleListPageResponseDto> getTop3RecentCodeArticle(int page, int size) {
        Page<CodeArticle> codeArticlePage = codeArticleRepository.findCodeArticleRecent(PageRequest.of(page, size));
        PageResponseDto pageInfo = PageResponseDto.codeArticleDto(codeArticlePage);

        List<CodeArticleListResponseDto> recentCodeArticleListDto = new ArrayList<>();

        codeArticlePage.getContent().forEach(s -> recentCodeArticleListDto.add(CodeArticleListResponseDto.toDto(s, userFeignClient.getUserInfo(s.getArticle().getBlog().getUserId()))));
        return Response.success(CodeArticleListPageResponseDto.toDto(pageInfo, recentCodeArticleListDto));
    }

    @Transactional
    public Response<CodeArticleResponseDto> post(String userId, CodeArticlePostRequestDto codeArticlePostRequestDto) throws Exception {
        List<String> urlAndContent = minioImageService.saveImage(codeArticlePostRequestDto.getContent());

        Article article = Article.builder()
                .blog(blogRepository.findByUserId(userId).get())
                .childCategory(childCategoryRepository.findById(codeArticlePostRequestDto.getChildCategoryId()).get())
                .title(codeArticlePostRequestDto.getTitle())
                .content(urlAndContent.get(1))
                .thumbnailImageUrl(urlAndContent.get(0))
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
    public Response<CodeArticleResponseDto> edit(Long articleId, CodeArticleEditRequestDto codeArticleEditRequestDto) throws Exception {
        CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();

        Article article = articleRepository.findById(codeArticle.getArticle().getId()).get();

        List<String> originalUrlAndContent = new ArrayList<>();
        originalUrlAndContent.add(0, article.getThumbnailImageUrl());
        originalUrlAndContent.add(1, codeArticleEditRequestDto.getContent());

        minioImageService.deleteImage(codeArticleEditRequestDto.getDeleteImageUrls());
        List<String> newUrlAndContent = minioImageService.editArticleImage(originalUrlAndContent);

        article.editCodeArticle(codeArticleEditRequestDto, passwordEncrypt(codeArticleEditRequestDto.getPassword()), newUrlAndContent);
        codeArticle.editCodeArticle(codeArticleEditRequestDto);

        articleRepository.save(article);
        codeArticleRepository.save(codeArticle);

        return Response.success(CodeArticleResponseDto.toDto(article, codeArticle));
    }

    public String getNickNameByUserId(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return userInfo.getNickName();
    }

    // 게시글 비밀번호 암호화 method
    public String passwordEncrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
