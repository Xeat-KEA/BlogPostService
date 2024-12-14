package xeat.blogservice.reply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 수정 요청 DTO")
public class ReplyEditRequestDto {

    @Schema(description = "수정 댓글 내용", example = "수정 댓글 내용 1")
    private String content;
}
