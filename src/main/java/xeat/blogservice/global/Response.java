package xeat.blogservice.global;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private Integer statusCode;
    private String message;
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
