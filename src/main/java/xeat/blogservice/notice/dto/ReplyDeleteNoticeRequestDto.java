package xeat.blogservice.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;

@Getter
@NoArgsConstructor
public class ReplyDeleteNoticeRequestDto {

    private Long replyId;

    private ReportCategory reasonCategory;
}
