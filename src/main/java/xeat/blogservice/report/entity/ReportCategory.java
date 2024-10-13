package xeat.blogservice.report.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum ReportCategory {

    @JsonProperty("스팸 및 광고")
    ADVERTISE("스팸 및 광고"),

    @JsonProperty("부적절한 내용")
    INAPPROPRIATE("부적절한 내용"),

    @JsonProperty("개인 정보 침해")
    INVASION("개인 정보 침해"),

    @JsonProperty("허위 사실 유포")
    SPREAD("허위 사실 유포"),

    @JsonProperty("직접 입력")
    ETC("직접 입력");

    private final String value;

    ReportCategory(String value) {
        this.value = value;
    }
}
