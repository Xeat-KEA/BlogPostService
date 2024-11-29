package xeat.blogservice.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.notice.dto.*;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.service.NoticeService;

@Tag(name = "블로그 알림", description = "블로그 알림 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "블로그 알림 목록 조회", description = "블로그 알림 목록 조회 시 필요한 API")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    @GetMapping("/list")
    public Response<NoticeListPageResponseDto> getNoticeList(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size,
                                                                  @RequestHeader("UserId") String userId)  {
        return noticeService.getNoticeList(page, size, userId);
    }

    @Operation(summary = "코딩테스트 문제 등록 처리", description = "관리자가 코딩테스트 문제 등록을 했을 때 알림에 저장하기 위해 필요한 API")
    @PostMapping("/code")
    public Response<Notice> saveCodeNotice(@RequestBody CodeNoticeSaveRequestDto codeNoticeSaveRequestDto) {
        return noticeService.saveCodeNotice(codeNoticeSaveRequestDto);
    }

    @Operation(summary = "블로그 알림 확인 처리", description = "블로그 알림 확인 처리에 필요한 API")
    @PutMapping("/check")
    public Response<NoticeCheckResponseDto> checkNotice(@RequestHeader("UserId") String userId) {
        return noticeService.checkNotice(userId);
    }
}
