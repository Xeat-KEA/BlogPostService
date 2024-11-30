package xeat.blogservice.codearticle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "코딩 게시글 수정 요청 DTO")
public class CodeArticleEditRequestDto {

    @Schema(description = "코딩 게시글 제목", example = "코딩 게시글 수정 제목 1")
    private String title;

    @Schema(description = "코딩 게시글 본문 내용", example = "코딩 게시글 수정 본문 내용 1")
    private String content;

    @Schema(description = "코딩 게시글 비밀글 여부", example = "비밀 글일시 true, 아닐시 false")
    private Boolean isSecret;

    @Schema(description = "코딩 게시글 비밀번호", example = "비밀번호 없을 시 null로 설정")
    private String password;

}
