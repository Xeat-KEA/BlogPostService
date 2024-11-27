package xeat.blogservice.reply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.global.Response;
import xeat.blogservice.reply.dto.ReplyEditRequestDto;
import xeat.blogservice.reply.dto.ReplyPostRequestDto;
import xeat.blogservice.reply.dto.ReplyResponseDto;
import xeat.blogservice.reply.service.ReplyService;

@Tag(name = "댓글", description = "댓글 관련 API")
@RestController
@RequestMapping("/blog/board/article/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @Operation(summary = "댓글 작성", description = "댓글 작성 시 필요한 API")
    @PostMapping
    public Response<ReplyResponseDto> replyPost(@RequestHeader("UserId") String userId, @RequestBody ReplyPostRequestDto replyPostRequestDto) {
        return replyService.replyPost(userId, replyPostRequestDto);
    }

    @Operation(summary = "댓글 수정", description = "댓글 수정에 필요한 API")
    @PutMapping("/{replyId}")
    public Response<ReplyResponseDto> replyEdit(@PathVariable Long replyId, @RequestBody ReplyEditRequestDto replyEditRequestDto) {
        return replyService.replyEdit(replyId, replyEditRequestDto);
    }

    @Operation(summary = "댓글 삭제", description = "댓글 삭제에 필요한 API")
    @DeleteMapping("/{replyId}")
    public Response<?> deleteReply(@PathVariable Long replyId) {

        return replyService.delete(replyId);
    }
}
