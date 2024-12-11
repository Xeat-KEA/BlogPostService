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
import xeat.blogservice.global.response.PageResponseDto;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.global.feignclient.CodeBankFeignClient;
import xeat.blogservice.global.feignclient.CodeBankInfoResponseDto;
import xeat.blogservice.global.feignclient.UserFeignClient;
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
    private final CodeBankFeignClient codeBankFeignClient;

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
                .writtenCode(codeArticlePostRequestDto.getWrittenCode())
                .build();

        codeArticleRepository.save(codeArticle);
        CodeBankInfoResponseDto codeBankInfo = codeBankFeignClient.getCodeBankInfo(codeArticle.getArticle().getBlog().getUserId(), codeArticle.getCodeId());

        return Response.success(CodeArticleResponseDto.toDto(codeArticle, codeBankInfo));
    }

    @Transactional
    public Response<CodeArticleResponseDto> edit(Long articleId, CodeArticleEditRequestDto codeArticleEditRequestDto) throws Exception {
        CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();

        Article article = articleRepository.findById(codeArticle.getArticle().getId()).get();

        List<String> newUrlAndContent = minioImageService.editArticleImage(codeArticleEditRequestDto.getContent(),
                                                                            article.getThumbnailImageUrl(),
                                                                            codeArticleEditRequestDto.getOriginalImageList());

        String password = codeArticleEditRequestDto.getPassword();

        if (password != null) {
            password = passwordEncrypt(password);
        }

        article.editCodeArticle(codeArticleEditRequestDto, password, newUrlAndContent);
        articleRepository.save(article);

        CodeBankInfoResponseDto codeBankInfo = codeBankFeignClient.getCodeBankInfo(codeArticle.getArticle().getBlog().getUserId(), codeArticle.getCodeId());

        return Response.success(CodeArticleResponseDto.toDto(codeArticle, codeBankInfo));
    }

    // 게시글 비밀번호 암호화 method
    public String passwordEncrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
