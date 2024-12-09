package xeat.blogservice.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xeat.blogservice.article.service.ArticleService;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.image.dto.UploadImageResponse;
import xeat.blogservice.image.service.ImageService;
import xeat.blogservice.notice.dto.ArticleNoticeRequestDto;
import xeat.blogservice.notice.dto.ReplyNoticeDeleteRequestDto;
import xeat.blogservice.reply.service.ReplyService;
import xeat.blogservice.report.dto.ReportListPageResponseDto;
import xeat.blogservice.report.service.UserReportService;

@Tag(name = "관리자", description = "관리자 서비스 관련 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final ArticleService articleService;
    private final ImageService imageService;
    private final ReplyService replyService;
    private final UserReportService userReportService;

    @Operation(summary = "게시글 삭제(관리자용)", description = "관리자가 게시글을 삭제처리 할 때 필요한 API")
    @DeleteMapping("/article")
    public Response<?> deleteArticleByAdmin(@RequestBody ArticleNoticeRequestDto articleNoticeRequestDto) {
        return articleService.deleteArticleByAdmin(articleNoticeRequestDto);
    }

    @Operation(summary = "사용자 이미지 업로드", description = "사용자가 이미지를 업로드 하였을 때 필요한 API")
    @Parameters({
            @Parameter(name = "image", description = "업로드할 이미지 파일", required = true)
    })
    @PostMapping("/upload")
    public UploadImageResponse uploadImage(@RequestParam("image") MultipartFile file) throws Exception {
        return imageService.uploadImage(file);
    }

    @Operation(summary = "댓글 삭제(관리자용)", description = "관리자가 댓글 삭제 처리할 때 필요한 API")
    @DeleteMapping("/reply")
    public Response<?> deleteReplyByAdmin(@RequestBody ReplyNoticeDeleteRequestDto replyNoticeDeleteRequestDto) {
        return replyService.deleteReplyByAdmin(replyNoticeDeleteRequestDto);
    }

    @Operation(summary = "게시글 신고 목록 10개 조회", description = "게시글 신고 목록을 10개씩 페이징 처리하여 반환")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "10", required = false)
    })
    @GetMapping("/article/report/list")
    public Response<ReportListPageResponseDto> getArticleReportList(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return userReportService.getArticleReportList(page, size);
    }

    @Operation(summary = "블로그 신고 목록 10개 조회", description = "블로그 신고 목록을 10개씩 페이징 처리하여 반환")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "10", required = false)
    })
    @GetMapping("/blog/report/list")
    public Response<ReportListPageResponseDto> getBlogReportList(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return userReportService.getBlogReportList(page, size);
    }

    @Operation(summary = "댓글 신고 목록 10개 조회", description = "댓글 신고 목록을 10개씩 페이징 처리하여 반환")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "10", required = false)
    })
    @GetMapping("/reply/report/list")
    public Response<ReportListPageResponseDto> getReplyReportList(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return userReportService.getReplyReportList(page, size);
    }
}
