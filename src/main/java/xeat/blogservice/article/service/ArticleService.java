package xeat.blogservice.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.dto.*;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.codearticle.dto.CodeArticleCategoryResponseDto;
import xeat.blogservice.codearticle.dto.CodeArticleListResponseDto;
import xeat.blogservice.codearticle.dto.GetCodeArticleResponseDto;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.minio.MinioImageService;
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.Response;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.recommend.repository.RecommendRepository;
import xeat.blogservice.reply.dto.ArticleReplyResponseDto;
import xeat.blogservice.reply.dto.ChildReplyResponseDto;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.repository.ReplyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final BlogRepository blogRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;
    private final ReplyRepository replyRepository;
    private final UserFeignClient userFeignClient;
    private final RecommendRepository recommendRepository;

    private final MinioImageService minioImageService;

    @Transactional
    public FeignClientTestDto getUserInfo(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return FeignClientTestDto.toDto(userInfo);
    }

    @Transactional
    public Boolean passwordCheck(Long articleId, String password) {
        return BCrypt.checkpw(password, articleRepository.findById(articleId).get().getPassword());
    }

    // 게시글 상세 조회
    @Transactional
    public Response<GetArticleResponseDto> getArticle(Long articleId, String userId) {
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

        // 사용자가 게시글 좋아요를 눌렀는지 여부
        Boolean checkRecommend = recommendRepository.existsByArticleIdAndUserUserId(articleId, userId);

        UserInfoResponseDto articleUserInfo = userFeignClient.getUserInfo(updateArticle.getBlog().getUserId());

        // 코딩테스트 게시글일 경우 codeArticleDto에 값을 담아서 반환하도록 처리
        if (codeArticleRepository.existsByArticleId(articleId)) {
            CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();
            return Response.success(GetCodeArticleResponseDto.toDto(updateArticle, codeArticle, articleUserInfo, articleReplyResponseDtoList, checkRecommend));
        }
        //일반 게시글일 경우 articleDto에 값을 담아서 반환하도록 처리
        else {
            return Response.success(GetArticleResponseDto.toDto(updateArticle, articleUserInfo, articleReplyResponseDtoList, checkRecommend));

        }
    }

    @Transactional
    public Response<ArticleListPageResponseDto> getAllArticleByBlogId(String userId, int page, int size) {

        Long blogId = blogRepository.findByUserId(userId).get().getId();
        Page<Article> articleList = articleRepository.findAllArticleByBlogId(PageRequest.of(page, size), blogId);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);

        List<ResponseDto> articleDtoList = new ArrayList<>();

        for (Article article : articleList) {
            if (codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle));
            }
            else {
                articleDtoList.add(ArticleCategoryResponseDto.toDto(article));
            }
        }

        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, articleDtoList));
    }

    @Transactional
    public Response<ArticleListPageResponseDto> getArticleBySearchWord(String searchWord, String userId, int page, int size) {
        Blog blog = blogRepository.findByUserId(userId).get();

        Page<Article> articleListContaining = articleRepository.findArticleListContaining(PageRequest.of(page, size), blog.getId(), searchWord);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleListContaining);

        List<ResponseDto> articleDtoList = new ArrayList<>();

        for (Article article : articleListContaining) {
            if(codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle));
            }
            else {
                articleDtoList.add(ArticleCategoryResponseDto.toDto(article));
            }
        }

        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, articleDtoList));
    }

    @Transactional
    public Response<ArticleListPageResponseDto> getArticleByChildCategory(int page, int size, Long blogId, Long childCategoryId) {
        Page<Article> articleList = articleRepository.findArticleChildCategoryId(PageRequest.of(page, size), blogId, childCategoryId);

        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);

        List<ResponseDto> articleCategoryResponseDtoList = new ArrayList<>();

        for (Article article : articleList) {
            if (codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                articleCategoryResponseDtoList.add(CodeArticleCategoryResponseDto.toDto(codeArticle));
            }
            else {
                articleCategoryResponseDtoList.add(ArticleCategoryResponseDto.toDto(article));
            }
        }
        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, articleCategoryResponseDtoList));
    }

    // 전체 게시글 최신순 3개 조회
    @Transactional
    public Response<ArticleListPageResponseDto> getTop3RecentAllArticle(int page, int size) {

        Page<Article> articleList = articleRepository.findAllArticleRecent(PageRequest.of(page, size));
        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);

        List<ResponseDto> recentAllArticleListDto = new ArrayList<>();

        for (Article article : articleList) {
            String nickName = getNickNameByUserId(article.getBlog().getUserId());
            if (codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                recentAllArticleListDto.add(CodeArticleListResponseDto.toDto(codeArticle, nickName));
            }
            else {
                recentAllArticleListDto.add(ArticleListResponseDto.toDto(article, nickName));
            }
        }

        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, recentAllArticleListDto));
    }

    // 일반 게시글 최신순 3개 조회
    @Transactional
    public Response<ArticleListPageResponseDto> getTop3RecentArticle(int page, int size) {
        Page<Article> articleList = articleRepository.findArticleRecent(PageRequest.of(page,size));
        PageResponseDto pageInfo = PageResponseDto.articleDto(articleList);
        List<ResponseDto> recentArticleListDto = new ArrayList<>();

        articleList.getContent().forEach(s -> recentArticleListDto.add(ArticleListResponseDto.toDto(s, getNickNameByUserId(s.getBlog().getUserId()))));
        return Response.success(ArticleListPageResponseDto.toDto(pageInfo, recentArticleListDto));
    }

    @Transactional
    public Response<?> getTop3LikeCountArticle() {
        Page<Article> articleLikeCountList = articleRepository.findArticleLikeCount(PageRequest.of(0, 5));

        List<ResponseDto> recentAllArticleListDto = new ArrayList<>();

        for (Article article : articleLikeCountList) {
            String nickName = getNickNameByUserId(article.getBlog().getUserId());
            if (codeArticleRepository.existsByArticleId(article.getId())) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(article.getId()).get();
                recentAllArticleListDto.add(CodeArticleListResponseDto.toDto(codeArticle, nickName));
            }
            else {
                recentAllArticleListDto.add(ArticleListResponseDto.toDto(article, nickName));
            }
        }

        return Response.success(recentAllArticleListDto);
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

        // Minio 서버 이미지 작업을 위해 기존 게시글의 썸네일 이미지 URL과 수정한 게시글 본문내용을 List에 저장
        List<String> originalUrlAndContent = new ArrayList<>();
        originalUrlAndContent.add(0, article.getThumbnailImageUrl());
        originalUrlAndContent.add(1, articleEditRequestDto.getContent());

        List<String> newUrlAndContent = minioImageService.editArticleImage(originalUrlAndContent);

        article.editArticle(articleEditRequestDto, passwordEncrypt(articleEditRequestDto.getPassword()),
                            childCategory, newUrlAndContent);
        Article updateArticle = articleRepository.save(article);
        return Response.success(ArticlePostResponseDto.toDto(updateArticle));
    }

    @Transactional
    public Response<?> delete(Long articleId) {
        articleRepository.deleteById(articleId);

        if (codeArticleRepository.existsByArticleId(articleId)) {
            codeArticleRepository.delete(codeArticleRepository.findByArticleId(articleId).get());
        }
        return new Response<>(200, "게시글 삭제 성공", null);
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

}