package xeat.blogservice.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.global.Response;
import xeat.blogservice.reply.dto.ReplyPostRequestDto;
import xeat.blogservice.reply.dto.ReplyPostResponseDto;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.service.ReplyService;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/blog/board/article/reply")
    public Response<ReplyPostResponseDto> replyPost(@RequestBody ReplyPostRequestDto replyPostRequestDto) {

        return replyService.replyPost(replyPostRequestDto);
    }

    @DeleteMapping("/blog/board/article/reply/delete/{replyId}")
    public Response<?> deleteReply(@PathVariable Long replyId) {

        return replyService.delete(replyId);
    }
}
