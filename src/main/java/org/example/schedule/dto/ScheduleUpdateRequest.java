package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {

    //제목, 작성자명만 수정
    private String title;
    private String author;
    private String password;
}
