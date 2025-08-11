package org.example.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class ScheduleSaveRequest {
    //id는 디비에서 생성
    private String title;
    private String content;
    private String author;
    private String password;
}
