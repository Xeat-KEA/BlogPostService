package xeat.blogservice.global.feignclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CodeBankInfoResponseDto {

    private String title;

    private String content;
}
