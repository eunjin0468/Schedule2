package org.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ScheduleUpdateRequest { //기존 일정을 수정할 때 클라이언트가 서버로 보내는 요청 데이터 구조 (요청 DTO)
    //제목, 내용만 수정

    @NotBlank(message = "제목은 필수입니다")
    @Size(max=100)
    private final String title;

    @Size(max=500)
    private final String content;

    public ScheduleUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
