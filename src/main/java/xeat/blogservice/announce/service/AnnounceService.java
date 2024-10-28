package xeat.blogservice.announce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.announce.dto.AnnounceListPageResponseDto;
import xeat.blogservice.announce.dto.AnnounceListResponseDto;
import xeat.blogservice.announce.dto.GetAnnounceResponseDto;
import xeat.blogservice.announce.entity.Announce;
import xeat.blogservice.announce.repository.AnnounceRepository;
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.Response;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnounceService {

    private final AnnounceRepository announceRepository;

    @Transactional
    public Response<GetAnnounceResponseDto> getAnnounce(Long announceId) {
        Announce announce = announceRepository.findById(announceId).get();
        return Response.success(GetAnnounceResponseDto.toDto(announce));
    }

    @Transactional
    public Response<AnnounceListPageResponseDto> getAnnounceList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Announce> announcePageList = announceRepository.findAllByOrderByCreatedDateDesc(pageable);
        List<AnnounceListResponseDto> announceListResponseDto = new ArrayList<>();

        PageResponseDto pageInfo = PageResponseDto.toDto(announcePageList);
        announcePageList.getContent().forEach(s -> announceListResponseDto.add(AnnounceListResponseDto.toDto(s)));

        return Response.success(AnnounceListPageResponseDto.toDto(pageInfo, announceListResponseDto));
    }

}
