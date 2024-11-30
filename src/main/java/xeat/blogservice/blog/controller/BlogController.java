package xeat.blogservice.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.blog.dto.*;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.service.BlogService;
import xeat.blogservice.global.response.Response;

@Tag(name = "블로그", description = "블로그 관련 API")
@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @Operation(summary = "blogId 조회", description = "토큰 값을 포함시켜 요청하고 그 사용자의 blogId를 반환할 때 필요한 API")
    @GetMapping
    public Response<BlogIdResponseDto> getBlogId(@RequestHeader("UserId") String userId) {
        return blogService.getBlogId(userId);
    }

    @Operation(summary = "User-Service에서 blogId 조회", description = "FeignClient로 User-Service에서 blogId 요청할때 필요한 API")
    @GetMapping("/user")
    public Long getBlogIdFromUser(@RequestHeader("UserId") String userId) {
        return blogService.getBlogIdFromUser(userId);
    }

    @Operation(summary = "블로그 홈 화면 조회", description = "블로그 홈 화면 조회에 필요한 API")
    @GetMapping("/home/{blogId}")
    public Response<BlogLoginHomeResponseDto> getLoginBlogHome(@RequestHeader("UserId") String userId, @PathVariable Long blogId) {
        return blogService.getLoginBlogHome(userId, blogId);
    }

    @Operation(summary = "블로그 알림 확인 여부 조회", description = "사용자가 블로그로 온 알림을 확인했는지 여부를 파악하기 위해 필요한 API")
    @GetMapping("/notice/check")
    public Response<BlogNoticeCheckResponseDto> getNoticeCheck(@RequestHeader("UserId") String userId) {
        return blogService.getNoticeCheck(userId);
    }

    @Operation(summary = "User-Service에서 블로그 소개 글과 blogId 조회", description = "FeignClient로 User-Service에서 blogId와 블로그 소개글 요청할 때 필요한 API")
    @GetMapping("/home/mainContent")
    public Response<BlogMainContentResponseDto> getMainContent(@RequestHeader("UserId") String userId) {
        return blogService.getMainContent(userId);
    }

    @Operation(summary = "블로그 소개글 수정 페이지 조회", description = "사용자가 블로그 수정 페이지로 넘어갈 때 기존 블로그 소개글 내용 조회에 필요한 API")
    @GetMapping("/home")
    public Response<BlogMainContentResponseDto> getEditMainContent(@RequestHeader("UserId") String userId) throws Exception {
        return blogService.getEditMainContent(userId);
    }

    @Operation(summary = "블로그 생성", description = "블로그를 생성할 때 필요한 API")
    @PostMapping
    public Response<?> createBlog(@RequestHeader("UserId") String userId) {
        return blogService.create(userId);
    }

    @Operation(summary = "블로그 소개글 수정", description = "블로그 소개 글을 수정할 떄 필요한 API")
    @PutMapping("/home")
    public Response<BlogMainContentResponseDto> editMainContent(@RequestHeader("UserId") String userId,
                                          @RequestBody BlogEditRequestDto blogEditRequestDto) throws Exception{
        return blogService.editMainContent(userId, blogEditRequestDto);
    }

    @Operation(summary = "블로그 알림 체크 변경", description = "사용자가 종 모양을 클릭했을 때 블로그 알림 확인 상태를 true로 변경하기 위해 필요한 API")
    @PutMapping("/notice/submit")
    public Response<BlogNoticeCheckResponseDto> editNotice(@RequestHeader("UserId") String userId) {
        return blogService.editNotice(userId);
    }

    @Operation(summary = "블로그 삭제", description = "회원 탈퇴가 진행되면 블로그도 연쇄적으로 삭제되고 이때 필요한 API")
    @DeleteMapping
    public Response<?> deleteBlog(@RequestHeader("UserId") String userId) {
        return blogService.deleteBlog(userId);
    }
}
