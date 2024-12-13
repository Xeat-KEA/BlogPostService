package xeat.blogservice.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import xeat.blogservice.article.dto.*;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.codearticle.dto.*;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.response.PageResponseDto;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.global.response.ResponseDto;
import xeat.blogservice.global.feignclient.CodeBankFeignClient;
import xeat.blogservice.global.feignclient.CodeBankInfoResponseDto;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.image.service.ImageService;
import xeat.blogservice.notice.dto.ArticleNoticeRequestDto;
import xeat.blogservice.notice.service.NoticeService;
import xeat.blogservice.parentcategory.entity.ParentCategory;
import xeat.blogservice.parentcategory.repository.ParentCategoryRepository;
import xeat.blogservice.recommend.repository.RecommendRepository;
import xeat.blogservice.reply.dto.ArticleReplyResponseDto;
import xeat.blogservice.reply.dto.ChildReplyResponseDto;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.repository.ReplyRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final BlogRepository blogRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final ParentCategoryRepository parentCategoryRepository;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;
    private final ReplyRepository replyRepository;
    private final UserFeignClient userFeignClient;
    private final CodeBankFeignClient codeBankFeignClient;
    private final RecommendRepository recommendRepository;
    private final BestArticleCacheService bestArticleCacheService;
    private final ImageService minioImageService;
    private final NoticeService noticeService;

    @Transactional
    public FeignClientTestDto getUserInfo(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return FeignClientTestDto.toDto(userInfo);
    }

    @Transactional
    public Boolean passwordCheck(Long articleId, String password) {
        return BCrypt.checkpw(password, articleRepository.findById(articleId).get().getPassword());
    }

    // 비회원 게시글 상세 조회
    @Transactional
    public Response<GetArticleResponseNonUserDto> getNonUserArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).get();

        // 게시글 조회수 +1 처리
        article.plusViewCount();

        Article updateArticle = articleRepository.save(article);

        List<Reply> replyList = replyRepository.findParentReplies(articleId);

        List<ArticleReplyResponseDto> articleReplyResponseDtoList = new ArrayList<>();


        for (Reply reply : replyList) {
            UserInfoResponseDto replyUserInfo = userFeignClient.getUserInfo(reply.getUser().getUserId());
            articleReplyResponseDtoList.add(ArticleReplyResponseDto.toDto(reply, replyUserInfo, makeChildListDto(reply)));
        }

        UserInfoResponseDto articleUserInfo = userFeignClient.getUserInfo(updateArticle.getBlog().getUserId());

        // 코딩테스트 게시글일 경우 codeArticleDto에 값을 담아서 반환하도록 처리
        if (codeArticleRepository.existsByArticleId(articleId)) {
            CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();
            CodeBankInfoResponseDto codeBankInfo = codeBankFeignClient.getCodeBankInfo(codeArticle.getArticle().getBlog().getUserId(), codeArticle.getCodeId());
            return Response.success(GetCodeArticleResponseNonUserDto.toDto(codeArticle, articleUserInfo, codeBankInfo, articleReplyResponseDtoList));
        }
        //일반 게시글일 경우 articleDto에 값을 담아서 반환하도록 처리
        else {
            return Response.success(GetArticleResponseNonUserDto.toDto(updateArticle, articleUserInfo, articleReplyResponseDtoList));

        }
    }

    // 회원 게시글 상세 조회
    @Transactional
    public Response<GetArticleResponseLoginDto> getUserArticle(Long articleId, String userId) {
        Article article = articleRepository.findById(articleId).get();
        Blog user = blogRepository.findByUserId(userId).get();
        String updateContent = article.getContent().replaceAll("<(\\w+)>([^<>]*)<\\/\\1>", "");
        log.info("게시글 본문 내용 = {}", updateContent);

        // 게시글 조회수 +1 처리
        article.plusViewCount();

        Article updateArticle = articleRepository.save(article);
        log.info("게시글 생성시간={}", updateArticle.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        List<Reply> replyList = replyRepository.findParentReplies(articleId);

        List<ArticleReplyResponseDto> articleReplyResponseDtoList = new ArrayList<>();


        for (Reply reply : replyList) {
            UserInfoResponseDto replyUserInfo = userFeignClient.getUserInfo(reply.getUser().getUserId());
            articleReplyResponseDtoList.add(ArticleReplyResponseDto.toDto(reply, replyUserInfo, makeChildListDto(reply)));
        }

        // 사용자가 게시글 좋아요를 눌렀는지 여부
        Boolean checkRecommend = recommendRepository.existsByArticleAndUser(article, user);

        UserInfoResponseDto articleUserInfo = userFeignClient.getUserInfo(updateArticle.getBlog().getUserId());

        // 코딩테스트 게시글일 경우 codeArticleDto에 값을 담아서 반환하도록 처리
        if (codeArticleRepository.existsByArticleId(articleId)) {
            CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();
            CodeBankInfoResponseDto codeBankInfo = codeBankFeignClient.getCodeBankInfo(codeArticle.getArticle().getBlog().getUserId(), codeArticle.getCodeId());
            return Response.success(GetCodeArticleResponseLoginDto.toDto(codeArticle, articleUserInfo, codeBankInfo, articleReplyResponseDtoList, checkRecommend));
        }
        //일반 게시글일 경우 articleDto에 값을 담아서 반환하도록 처리
        else {
            return Response.success(GetArticleResponseLoginDto.toDto(updateArticle, articleUserInfo, articleReplyResponseDtoList, checkRecommend));

        }
    }

    @Transactional
    public Response<ArticleListPageResponseDto> getAllArticleByBlogId(Long blogId, int page, int size) {

        Page<Article> articleList = articleRepository.findAllArticleByBlogId(PageRequest.of(page, size), blogId);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);

        List<ResponseDto> articleDtoList = new ArrayList<>();

        for (Article article : articleList) {
            String content = article.getContent().replaceAll("<[^>]*>", "");
            if (codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle, content));
            }
            else {
                articleDtoList.add(ArticleCategoryResponseDto.toDto(article, content));
            }
        }

        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, blogId, articleList.getTotalElements(), articleDtoList));
    }

    @Transactional
    public Response<ArticleListPageResponseDto> getArticleBySearchWord(String searchWord, Long blogId, int page, int size) {
        Blog blog = blogRepository.findById(blogId).get();

        Page<Article> articleListContaining = articleRepository.findArticleListContaining(PageRequest.of(page, size), blog.getId(), searchWord);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleListContaining);

        List<ResponseDto> articleDtoList = new ArrayList<>();

        for (Article article : articleListContaining) {
            String content = translateContent(article.getContent().replaceAll("<[^>]*>", ""), searchWord);
            if(codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle, content));
            }
            else {
                articleDtoList.add(ArticleCategoryResponseDto.toDto(article, content));
            }
        }

        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, blogId, articleListContaining.getTotalElements(), articleDtoList));
    }

    @Transactional
    public Response<ArticleListPageResponseDto> getParentCategorySearchWord(String searchWord, Long blogId, int page, int size, Long parentCategoryId) {
        Blog blog = blogRepository.findById(blogId).get();

        Page<Article> articleListContaining = articleRepository.findArticleListByParentCategory(PageRequest.of(page, size), blog.getId(), searchWord, parentCategoryId);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleListContaining);

        List<ResponseDto> articleDtoList = new ArrayList<>();

        for (Article article : articleListContaining) {
            String content = translateContent(article.getContent().replaceAll("<[^>]*>", ""), searchWord);
            if(codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle, content));
            }
            else {
                articleDtoList.add(ArticleCategoryResponseDto.toDto(article, content));
            }
        }

        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, blogId, articleListContaining.getTotalElements(), articleDtoList));
    }

    @Transactional
    public Response<ArticleListPageResponseDto> getChildCategorySearchWord(String searchWord, Long blogId, int page, int size, Long childCategoryId) {
        Blog blog = blogRepository.findById(blogId).get();

        Page<Article> articleListContaining = articleRepository.findArticleListContainingChildCategory(PageRequest.of(page, size), blog.getId(), searchWord, childCategoryId);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleListContaining);

        List<ResponseDto> articleDtoList = new ArrayList<>();

        for (Article article : articleListContaining) {
            String content = translateContent(article.getContent().replaceAll("<[^>]*>", ""), searchWord);
            if(codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle, content));
            }
            else {
                articleDtoList.add(ArticleCategoryResponseDto.toDto(article, content));
            }
        }

        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, blogId, articleListContaining.getTotalElements(), articleDtoList));
    }


    @Transactional
    public Response<ArticleListPageResponseDto> getArticleByParentCategory(int page, int size, Long blogId, Long parentCategoryId) {

        if (blogId == null) {
            blogId = parentCategoryRepository.findById(parentCategoryId).get().getBlog().getId();
        }

        Page<Article> articleList = articleRepository.findArticleParentCategoryId(PageRequest.of(page, size), parentCategoryId, blogId);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);

        List<ResponseDto> articleCategoryResponseDtoList = new ArrayList<>();

        for (Article article : articleList) {
            String content = article.getContent().replaceAll("<[^>]*>", "");
            if (codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleCategoryResponseDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle, content));
            }
            else {
                articleCategoryResponseDtoList.add(ArticleCategoryResponseDto.toDto(article, content));
            }
        }
        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, blogId, articleList.getTotalElements(), articleCategoryResponseDtoList));
    }

    @Transactional
    public Response<ArticleListPageResponseDto> getArticleByChildCategory(int page, int size, Long blogId, Long childCategoryId) {

        if (blogId == null) {
            blogId = childCategoryRepository.findById(childCategoryId).get().getParentCategory().getBlog().getId();
        }

        Page<Article> articleList = articleRepository.findArticleChildCategoryId(PageRequest.of(page, size), blogId, childCategoryId);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);

        List<ResponseDto> articleCategoryResponseDtoList = new ArrayList<>();

        for (Article article : articleList) {
            String content = article.getContent().replaceAll("<[^>]*>", "");
            if (codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleCategoryResponseDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle, content));
            }
            else {
                articleCategoryResponseDtoList.add(ArticleCategoryResponseDto.toDto(article, content));
            }
        }
        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, blogId, articleList.getTotalElements(), articleCategoryResponseDtoList));
    }

    // 전체 게시글 최신순 3개 조회
    @Transactional
    public Response<ArticleListPageResponseDto> getTop3RecentAllArticle(int page, int size) {

        Page<Article> articleList = articleRepository.findAllArticleRecent(PageRequest.of(page, size));
        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);

        List<ResponseDto> recentAllArticleListDto = new ArrayList<>();

        for (Article article : articleList) {
            UserInfoResponseDto userInfo = userFeignClient.getUserInfo(article.getBlog().getUserId());
            if (codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                recentAllArticleListDto.add(CodeArticleListResponseDto.toDto(codeArticle, userInfo));
            }
            else {
                recentAllArticleListDto.add(ArticleListResponseDto.toDto(article, userInfo));
            }
        }

        Long blogId = 0L;

        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, blogId, articleList.getTotalElements(), recentAllArticleListDto));
    }

    // 일반 게시글 최신순 3개 조회
    @Transactional
    public Response<ArticleListPageResponseDto> getTop3RecentArticle(int page, int size) {
        Page<Article> articleList = articleRepository.findArticleRecent(PageRequest.of(page,size));
        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);
        List<ResponseDto> recentArticleListDto = new ArrayList<>();

        articleList.getContent().forEach(s -> recentArticleListDto.add(ArticleListResponseDto.toDto(s, userFeignClient.getUserInfo(s.getBlog().getUserId()))));

        Long blogId = 0L;
        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, blogId, articleList.getTotalElements(), recentArticleListDto));
    }


    @Transactional
    public Response<ArticleEditResponseDto> getArticleEdit(Long articleId) throws Exception {

        Article article = articleRepository.findById(articleId).get();
        List<String> originalImageList = minioImageService.getOriginalImageList(article.getContent());

        String updateContent = null;

        if (article.getContent() != null) {
            updateContent = Base64.getEncoder().encodeToString(article.getContent().getBytes());
        }

        return Response.success(ArticleEditResponseDto.toDto(article, updateContent, originalImageList));
    }

    @Transactional
    public Response<ArticlePostResponseDto> post(String userId, ArticlePostRequestDto articlePostRequestDto) throws Exception{

        List<String> urlAndContent = minioImageService.saveImage(articlePostRequestDto.getContent());

        String password = articlePostRequestDto.getPassword();

        if (password != null) {
            password = passwordEncrypt(articlePostRequestDto.getPassword());
        }

        Article article = Article.builder()
                .blog(blogRepository.findByUserId(userId).get())
                .childCategory(childCategoryRepository.findById(articlePostRequestDto.getChildCategoryId()).get())
                .title(articlePostRequestDto.getTitle())
                .content(urlAndContent.get(1))
                .thumbnailImageUrl(urlAndContent.get(0))
                .isSecret(articlePostRequestDto.getIsSecret())
                .password(password)
                .build();

        return Response.success(ArticlePostResponseDto.toDto(articleRepository.save(article)));
    }

    @Transactional
    public Response<ArticlePostResponseDto> edit(Long articleId, ArticleEditRequestDto articleEditRequestDto) throws Exception {

        Article article = articleRepository.findById(articleId).get();
        ChildCategory childCategory = childCategoryRepository.findById(articleEditRequestDto.getChildCategoryId()).get();

        List<String> newUrlAndContent = minioImageService.editArticleImage(articleEditRequestDto.getContent(),
                                                                            article.getThumbnailImageUrl(),
                                                                            articleEditRequestDto.getOriginalImageList());
        log.info("thumbnailImageUrl={}", newUrlAndContent.get(0));
        log.info("mainContent={}", newUrlAndContent.get(1));


        String password = articleEditRequestDto.getPassword();

        if (password != null) {
            password = passwordEncrypt(password);
        }

        article.editArticle(articleEditRequestDto, password,
                            childCategory, newUrlAndContent);
        Article updateArticle = articleRepository.save(article);

        return Response.success(ArticlePostResponseDto.toDto(updateArticle));
    }

    @Transactional
    public Response<ArticlePostResponseDto> editBlind(ArticleNoticeRequestDto articleNoticeRequestDto) {

        Article article = articleRepository.findById(articleNoticeRequestDto.getArticleId()).get();
        bestArticleCacheService.deleteArticle(article.getId());
        if (article.getIsBlind()) {
            article.updateIsBlindFalse(false);
            Article updateArticle = articleRepository.save(article);

            noticeService.saveArticleNonBlindNotice(updateArticle);
            return new Response<>(200, "게시글 블라인드 해제 성공", ArticlePostResponseDto.toDto(updateArticle));
        }
        else {
            article.updateIsBlindTrue(true);
            Article updateArticle = articleRepository.save(article);

            Blog blog = blogRepository.findById(article.getBlog().getId()).get();
            blog.updateNoticeCheckFalse();
            blogRepository.save(blog);

            noticeService.saveArticleBlindNotice(updateArticle, articleNoticeRequestDto);
            return new Response<>(200, "게시글 블라인드 처리 성공", ArticlePostResponseDto.toDto(updateArticle));
        }
    }

    @Transactional
    public Response<?> delete(Long articleId) {

        articleRepository.deleteById(articleId);

        if (bestArticleCacheService.deleteArticle(articleId)) {
            return new Response<>(200, "게시글 삭제 성공 및 베스트 게시글 업데이트 완료", null);
        }

        return new Response<>(200, "게시글 삭제 성공", null);
    }


    @Transactional
    public Response<?> deleteArticleByAdmin(ArticleNoticeRequestDto articleNoticeRequestDto) {

        Article article = articleRepository.findById(articleNoticeRequestDto.getArticleId()).get();

        Blog blog = blogRepository.findById(article.getBlog().getId()).get();
        blog.updateNoticeCheckFalse();
        blogRepository.save(blog);

        noticeService.saveArticleDeleteNotice(article, articleNoticeRequestDto);

        articleRepository.deleteById(article.getId());

        if (bestArticleCacheService.deleteArticle(articleNoticeRequestDto.getArticleId())) {
            return new Response<>(200, "게시글 삭제 성공 및 베스트 게시글 업데이트 완료", null);
        }

        return new Response<>(200, "게시글 삭제 및 알림 등록 성공", null);
    }

    // 부모 댓글에 달린 모든 대댓글 dto에 추가하는 method
    public List<ChildReplyResponseDto> makeChildListDto(Reply reply) {
        List<Reply> childReplyList = replyRepository.findAllByParentReplyId(reply.getId());
        List<ChildReplyResponseDto> childReplyResponseDtoList = new ArrayList<>();

        childReplyList.forEach(s -> childReplyResponseDtoList.add(ChildReplyResponseDto.toDto(
                s, userFeignClient.getUserInfo(s.getUser().getUserId()), getNickNameByUserId(s.getMentionedUser().getUserId()))));
        return childReplyResponseDtoList;
    }

    // userId로 해당 사용자의 닉네임 가져오는 method
    public String getNickNameByUserId(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return userInfo.getNickName();
    }

    // 게시글 비밀번호 암호화 method
    public String passwordEncrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String translateContent(String content, String searchWord) {
        log.info("content={}", content.replaceAll(searchWord, "<b>" + searchWord + "</b>"));
        return content.replaceAll(searchWord, "<b>" + searchWord + "</b>");
    }

}