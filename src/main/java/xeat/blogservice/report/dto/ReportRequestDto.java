package xeat.blogservice.report.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;

@Getter
@NoArgsConstructor
@Schema(description = "신고 요청 관련 DTO")
public class ReportRequestDto {

    @Schema(description = "신고한 사용자 고유 ID", example = "1")
    private String reporterId;

    @Schema(description = "신고 카테고리", example = "직접 입력")
    private ReportCategory reportCategory;

    @Schema(description = "신고 카테고리가 기타 일시 사용자가 직접 작성한 카테고리", example = "reportCategory가 직접 입력 일시 사용자가 직접 작성한 카테고리")
    private String directCategory;
}
