package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleSimpleResponse {
    private final Long scheduleId;
    private final String title;
    private final String content;

    public ScheduleSimpleResponse(Long scheduleId, String title, String content) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.content = content;
    }
}
