package xeat.blogservice.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.report.entity.UserReport;

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
