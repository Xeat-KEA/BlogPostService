package xeat.blogservice.codearticle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CodeArticleEditRequestDto {

    private String title;

    private String content;

    private Boolean isSecret;

    private String password;


    private String codeContent;

    private String writtenCode;
}
