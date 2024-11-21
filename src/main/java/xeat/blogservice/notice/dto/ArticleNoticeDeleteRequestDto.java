package xeat.blogservice.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleNoticeDeleteRequestDto {

    private Long articleId;

    private ReportCategory reasonCategory;
}
