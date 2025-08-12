package org.example.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleSaveRequest { //일정 등록 요청 시 클라이언트가 서버로 보내는 데이터 구조
    //id는 데이터베이스에서 자동 생성
    private Long userId;
    private String title;
    private String content;

    public ScheduleSaveRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
