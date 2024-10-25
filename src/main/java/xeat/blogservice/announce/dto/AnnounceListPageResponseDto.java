package xeat.blogservice.announce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.PageResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnounceListPageResponseDto {

    PageResponseDto pageInfo;

    List<AnnounceListResponseDto> announceList;

    public static AnnounceListPageResponseDto toDto(PageResponseDto pageInfo, List<AnnounceListResponseDto> announceList) {
        return new AnnounceListPageResponseDto(
                pageInfo,
                announceList
        );
    }
}
