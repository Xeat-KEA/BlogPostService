package xeat.blogservice.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "API 응답 형식")
public class Response<T> {

    @Schema(description = "상태 코드", example = "200")
    private Integer statusCode;

    @Schema(description = "응답 메시지", example = "요청 성공")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    // 성공 응답 생성
    public static <T> Response<T> success(T data) {
        return new Response<>(200, "요청 성공", data);
    }

    // 성공 응답 생성
    public static <T> Response<T> error(Integer statusCode, String message, T data) {
        return new Response<>(statusCode, message, data);
    }
}
