package org.example.schedule.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ScheduleUpdateRequest { //기존 일정을 수정할 때 클라이언트가 서버로 보내는 요청 데이터 구조 (요청 DTO)
    //제목, 내용만 수정
    private final String title;
    private final String content;
    private Long userId;
    private String password;

    public ScheduleUpdateRequest(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
