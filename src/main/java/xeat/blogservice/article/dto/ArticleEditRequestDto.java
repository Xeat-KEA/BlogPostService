package xeat.blogservice.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일반 게시글 수정 요청 DTO")
public class ArticleEditRequestDto {

    @Schema(description = "하위 게시판 고유 ID", example = "1")
    private Long childCategoryId;

    @Schema(description = "수정된 게시글 제목", example = "수정된 게시글 제목1")
    private String title;

    @Schema(description = "수정된 게시글 내용", example = "수정된 게시글 내용1")
    private String content;

    @Schema(description = "게시글 비밀글 여부", example = "비밀글이면 true, 비밀글이 아닐시 false")
    private Boolean isSecret;

    @Schema(description = "게시글 비밀번호", example = "비밀번호 설정 없을 시 null")
    private String password;
}
