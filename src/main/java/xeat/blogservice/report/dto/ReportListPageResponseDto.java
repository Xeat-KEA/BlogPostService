package xeat.blogservice.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.response.PageResponseDto;
import xeat.blogservice.global.response.ResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportListPageResponseDto {

    private PageResponseDto pageInfo;

    private List<ResponseDto> reportList;

    public static ReportListPageResponseDto toDto(PageResponseDto pageInfo, List<ResponseDto> responseDtoList) {
        return new ReportListPageResponseDto(
                pageInfo,
                responseDtoList
        );
    }
}
