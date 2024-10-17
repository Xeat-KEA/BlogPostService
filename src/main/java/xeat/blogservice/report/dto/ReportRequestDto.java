package xeat.blogservice.report.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;

@Getter
@NoArgsConstructor
public class ReportRequestDto {

    private Long reporterId;

    private ReportCategory reportCategory;

    private String directCategory;
}
