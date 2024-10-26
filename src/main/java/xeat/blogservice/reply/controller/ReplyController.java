package xeat.blogservice.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.global.Response;
import xeat.blogservice.reply.dto.ReplyEditRequestDto;
import xeat.blogservice.reply.dto.ReplyPostRequestDto;
import xeat.blogservice.reply.dto.ReplyResponseDto;
import xeat.blogservice.reply.service.ReplyService;

@RestController
@RequestMapping("/blog/board/article/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;


    @PostMapping
    public Response<ReplyResponseDto> replyPost(@RequestBody ReplyPostRequestDto replyPostRequestDto) {
        return replyService.replyPost(replyPostRequestDto);
    }

    @PutMapping("/{replyId}")
    public Response<ReplyResponseDto> replyEdit(@PathVariable Long replyId, @RequestBody ReplyEditRequestDto replyEditRequestDto) {
        return replyService.replyEdit(replyId, replyEditRequestDto);
    }

    @DeleteMapping("/{replyId}")
    public Response<?> deleteReply(@PathVariable Long replyId) {

        return replyService.delete(replyId);
    }
}
