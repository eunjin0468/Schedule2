package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateResponse {
    private final String title;
    private final String author;
    private final String password;

    public ScheduleUpdateResponse(String title, String author, String password) {
        this.title = title;
        this.author = author;
        this.password = password;
    }
}
