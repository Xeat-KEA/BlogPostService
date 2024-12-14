package xeat.blogservice.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;

@Getter
@NoArgsConstructor
public class ReplyNoticeDeleteRequestDto {

    private Long replyId;

    private ReportCategory reasonCategory;

    private String directCategory;
}
