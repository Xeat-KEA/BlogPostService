package xeat.blogservice.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;
import xeat.blogservice.report.entity.UserReport;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogReportResponseDto {

    private Long blogId;

    private ReportCategory reportCategory;

    private String directCategory;

    public static BlogReportResponseDto toDto(UserReport userReport) {
        return new BlogReportResponseDto(
                userReport.getBlog().getId(),
                userReport.getReportCategory(),
                userReport.getDirectCategory());
    }
}
