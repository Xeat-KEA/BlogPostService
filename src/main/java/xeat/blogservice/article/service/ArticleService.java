package xeat.blogservice.article.service;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.Response;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.global.userclient.UserFeignClient;
import xeat.blogservice.global.userclient.UserInfoResponseDto;
import xeat.blogservice.recommend.repository.RecommendRepository;
import xeat.blogservice.reply.dto.ArticleReplyResponseDto;
import xeat.blogservice.reply.dto.ChildReplyResponseDto;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.repository.ReplyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final MinioClient minioClient;

    @Value("${minio.url}")
    private String minioBaseUrl;

    @Value("${minio.articleUpload.bucket}")
    private String minioUploadBucket;
    @Value("${minio.uploadBucket.url}")
    private String uploadBucketUrl;
    @Value("${minio.articleSave.bucket}")
    private String minioSaveBucket;
    @Value("${minio.saveBucket.url}")
    private String saveBucketUrl;



    @Transactional
    public FeignClientTestDto getUserInfo(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return FeignClientTestDto.toDto(userInfo);
    }

    // 게시글 상세 조회
    @Transactional
    public Response<GetArticleResponseDto> getArticle(Long articleId, String userId) {
        Article article = articleRepository.findById(articleId).get();

        // 게시글 조회수 +1 처리
        article.plusViewCount();
        Article updateArticle = articleRepository.save(article);

        List<Reply> replyList = replyRepository.findParentReplies(articleId);
        for (Reply reply : replyList) {
            System.out.println(reply.getContent());
        }

        List<ArticleReplyResponseDto> articleReplyResponseDtoList = new ArrayList<>();

        replyList.forEach(s -> articleReplyResponseDtoList.add(ArticleReplyResponseDto.toDto(
                s, s.getUser().getUserId(), makeChildListDto(s)
        )));

        // 사용자가 게시글 좋아요를 눌렀는지 여부
        Boolean checkRecommend = recommendRepository.existsByArticleIdAndUserUserId(articleId, userId);

        // 코딩테스트 게시글일 경우 codeArticleDto에 값을 담아서 반환하도록 처리
        if (codeArticleRepository.existsByArticleId(articleId)) {
            CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();
            return Response.success(GetCodeArticleResponseDto.toDto(updateArticle, codeArticle, articleReplyResponseDtoList));
        }
        //일반 게시글일 경우 articleDto에 값을 담아서 반환하도록 처리
        else {
            return Response.success(GetArticleResponseDto.toDto(updateArticle, articleReplyResponseDtoList, checkRecommend));

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

        String content = saveArticleImage(articlePostRequestDto.getContent());

        Article article = Article.builder()
                .blog(blogRepository.findByUserId(userId).get())
                .childCategory(childCategoryRepository.findById(articlePostRequestDto.getChildCategoryId()).get())
                .title(articlePostRequestDto.getTitle())
                .content(content)
                .isSecret(articlePostRequestDto.getIsSecret())
                .password(articlePostRequestDto.getPassword())
                .build();
        return Response.success(ArticlePostResponseDto.toDto(articleRepository.save(article)));
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
        return new Response<>(200, "게시글 삭제 성공", null);
    }

    // 부모 댓글에 달린 모든 대댓글 dto에 추가하는 method
    public List<ChildReplyResponseDto> makeChildListDto(Reply reply) {
        List<Reply> childReplyList = replyRepository.findAllByParentReplyId(reply.getId());
        List<ChildReplyResponseDto> childReplyResponseDtoList = new ArrayList<>();

        childReplyList.forEach(s -> childReplyResponseDtoList.add(ChildReplyResponseDto.toDto(s, getNickNameByUserId(s.getUser().getUserId()), s.getMentionedUser().getUserId())));
        return childReplyResponseDtoList;
    }

    public String getNickNameByUserId(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return userInfo.getNickName();
    }

    public String saveArticleImage(String content) throws Exception {
        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/articleupload/(\\S+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String updateImagePath = matcher.group(0);
            String fileName = matcher.group(1);
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(minioSaveBucket)
                            .object(fileName)
                            .source(
                                    CopySource.builder()
                                            .bucket(minioUploadBucket)
                                            .object(fileName)
                                            .build())
                            .build());

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioUploadBucket)
                            .object(fileName)
                            .build()
            );
            content = content.replace(updateImagePath, saveBucketUrl + fileName);
        }
        return content;
    }

}