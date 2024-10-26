package xeat.blogservice.announce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.announce.dto.GetAnnounceResponseDto;
import xeat.blogservice.announce.service.AnnounceService;
import xeat.blogservice.global.Response;


@RestController
@RequestMapping("/blog/announce")
@RequiredArgsConstructor
public class AnnounceController {

    private final AnnounceService announceService;

    @GetMapping("/{announceId}")
    public Response<GetAnnounceResponseDto> getAnnounce(@PathVariable Long announceId) {
        return announceService.getAnnounce(announceId);
    }

    @GetMapping("/list")
    public Response<?> getAnnounceList(@PageableDefault(page=0, size=10) Pageable pageable) {
        return announceService.getAnnounceList(pageable.getPageNumber(), pageable.getPageSize());
    }
}
