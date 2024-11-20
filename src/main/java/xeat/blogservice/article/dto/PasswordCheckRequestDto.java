package xeat.blogservice.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 비밀번호 일치 여부 확인 요청 DTO")
public class PasswordCheckRequestDto {

    @Schema(description = "사용자가 입력한 게시글 비밀번호", example = "1234")
    String password;
}
