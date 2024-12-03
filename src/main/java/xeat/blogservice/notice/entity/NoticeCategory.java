package xeat.blogservice.notice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum NoticeCategory {

    @JsonProperty("댓글 알림")
    REPLY("댓글 알림"),

    @JsonProperty("팔로우 알림")
    FOLLOW("팔로우 알림"),

    @JsonProperty("코딩테스트 문제 등록 알림")
    CODE("코딩테스트 문제 등록 알림"),

    @JsonProperty("블라인드 처리 알림")
    BLIND("블라인드 처리 알림"),

    @JsonProperty("블라인드 해제 알림")
    NON_BLIND("블라인드 해제 알림"),

    @JsonProperty("삭제 처리 알림")
    DELETE("삭제 처리 알림");

    private final String value;

    NoticeCategory(String value) {
        this.value = value;
    }
}
