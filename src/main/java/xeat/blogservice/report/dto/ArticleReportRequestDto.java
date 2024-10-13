package xeat.blogservice.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleReportRequestDto {

    private ReportCategory reportCategory;

    private String directCategory;


}
