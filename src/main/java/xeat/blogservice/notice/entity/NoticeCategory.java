package xeat.blogservice.notice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum NoticeCategory {

    @JsonProperty("언급된 사용자 알림")
    MENTIONED_USER("언급된 사용자 알림"),

    @JsonProperty("댓글 알림")
    PARENT_REPLY("댓글 알림"),

    @JsonProperty("답글 알림")
    CHILD_REPLY("답글 알림"),

    @JsonProperty("팔로우 알림")
    FOLLOW("팔로우 알림"),

    @JsonProperty("코딩테스트 문제 등록 승인 알림")
    CODE_ADMIT("코딩테스트 문제 등록 승인 알림-"),

    @JsonProperty("코딩테스트 문제 등록 거절 알림")
    CODE_REFUSE("코딩테스트 문제 등록 거절 알림"),

    @JsonProperty("블라인드 처리 알림")
    BLIND("블라인드 처리 알림"),

    @JsonProperty("블라인드 해제 알림")
    NON_BLIND("블라인드 해제 알림"),

    @JsonProperty("게시글 삭제 처리 알림")
    ARTICLE_DELETE("게시글 삭제 처리 알림"),

    @JsonProperty("댓글 삭제 처리 알림")
    REPLY_DELETE("댓글 삭제 처리 알림");


    private final String value;

    NoticeCategory(String value) {
        this.value = value;
    }
}
