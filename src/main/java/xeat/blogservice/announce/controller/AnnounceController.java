package xeat.blogservice.announce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.announce.dto.AnnounceListPageResponseDto;
import xeat.blogservice.announce.dto.GetAnnounceResponseDto;
import xeat.blogservice.announce.service.AnnounceService;
import xeat.blogservice.global.Response;


@Tag(name = "공지사항", description = "공지사항 관련 API")
@RestController
@RequestMapping("/blog/announce")
@RequiredArgsConstructor
public class AnnounceController {

    private final AnnounceService announceService;

    @Operation(summary = "공지사항 상세 조회", description = "공지사항 하나를 클릭 하였을때 상세 조회")
    @GetMapping("/{announceId}")
    public Response<GetAnnounceResponseDto> getAnnounce(@PathVariable Long announceId) {
        return announceService.getAnnounce(announceId);
    }

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 페이지로 들어갔을때 공지사항 리스트를 조회")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 공지사항 개수", example = "10", required = false)
    })
    @GetMapping("/list")
    public Response<AnnounceListPageResponseDto> getAnnounceList(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return announceService.getAnnounceList(page, size);
    }
}
