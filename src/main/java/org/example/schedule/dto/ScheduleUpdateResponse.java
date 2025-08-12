package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateResponse { //일정 수정 요청이 성공한 후, 서버가 클라이언트에 반환하는 응답 DTO
    private final String title;
    private final String author;
    private final String password;

    public ScheduleUpdateResponse(String title, String author, String password) {
        this.title = title;
        this.author = author;
        this.password = password;
    }
}
