package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateRequest { //기존 일정을 수정할 때 클라이언트가 서버로 보내는 요청 데이터 구조 (요청 DTO)
    //제목, 작성자명만 수정
    private String title;
    private String author;
    private String password;
}
