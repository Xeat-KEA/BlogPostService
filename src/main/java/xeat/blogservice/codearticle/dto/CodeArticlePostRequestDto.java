package xeat.blogservice.codearticle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.entity.Difficulty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "코딩 게시글 작성 요청 API")
public class CodeArticlePostRequestDto {

    @Schema(description = "코딩 게시글 작성자 블로그 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "코딩 게시글 제목", example = "코딩 게시글 제목1")
    private String title;

    @Schema(description = "코딩 게시글 본문 내용", example = "코딩 게시글 본문 내용1")
    private String content;

    @Schema(description = "코딩 게시글 비밀글 여부", example = "비밀 글일 시 true, 아닐 시 false")
    private Boolean isSecret;

    @Schema(description = "코딩 게시글 비밀번호", example = "비밀번호가 없을 시 null로 설정")
    private String password;

    //코딩테스트 게시글만 별도로 저장
    @Schema(description = "코딩 게시글 난이도", example = "2단계")
    private Difficulty difficulty;

    @Schema(description = "코딩 게시글 문제 번호", example = "#1(코딩 게시글 문제 번호)")
    private String codeId;

    @Schema(description = "코딩 테스트 문제 제목 및 내용", example = "코딩 테스트 문제 제목 및 내용 1")
    private String codeContent;

    @Schema(description = "내가 작성한 답안 코드", example = "작성 답안 코드 1")
    private String writtenCode;
}
