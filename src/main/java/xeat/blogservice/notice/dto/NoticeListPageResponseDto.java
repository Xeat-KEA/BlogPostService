package xeat.blogservice.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.ResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListPageResponseDto {

    private PageResponseDto pageInfo;

    private List<ResponseDto> noticeList;

    public static NoticeListPageResponseDto toDto(PageResponseDto pageInfo, List<ResponseDto> noticeList) {
        return new NoticeListPageResponseDto(
                pageInfo,
                noticeList
        );
    }
}
