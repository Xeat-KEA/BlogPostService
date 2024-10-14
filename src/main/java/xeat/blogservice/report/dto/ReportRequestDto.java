package xeat.blogservice.report.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;

@Getter
@NoArgsConstructor
public class ReportRequestDto {

    private ReportCategory reportCategory;

    private String directCategory;
}
