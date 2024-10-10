package xeat.blogservice.codearticle.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Difficulty {
    @JsonProperty("1단계")
    LEVEL1("1단계"),

    @JsonProperty("2단계")
    LEVEL2("2단계"),

    @JsonProperty("3단계")
    LEVEL3("3단계"),

    @JsonProperty("4단계")
    LEVEL4("4단계"),

    @JsonProperty("5단계")
    LEVEL5("5단계");


    private final String value;

    Difficulty(String value) {
        this.value = value;
    }
}
