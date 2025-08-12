package org.example.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleSaveRequest { //일정 등록(생성) 요청 시 클라이언트가 서버로 보내는 데이터 구조 (요청 DTO)
    //id는 데이터베이스에서 자동 생성
    private String title;
    private String content;
    private String author;
    private String password;
}
