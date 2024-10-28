package xeat.blogservice.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일반 게시글 작성 요청 DTO")
public class ArticlePostRequestDto {

    @Schema(description = "게시글 작성자 블로그 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "게시글이 존재하는 하위게시판 고유 ID", example = "1")
    private Long childCategoryId;

    @Schema(description = "게시글 제목", example = "제목1")
    private String title;

    @Schema(description = "게시글 본문 내용", example = "게시글 본문 내용1")
    private String content;

    @Schema(description = "게시글 비밀글 여부", example = "비밀글 설정시 true, 아닐시 false")
    private Boolean isSecret;

    @Schema(description = "게시글 비밀번호 설정(없을시 null)", example = "비밀번호 없을 시 null")
    private String password;
}
