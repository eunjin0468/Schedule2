package org.example.schedule.dto;

import lombok.Getter;
import org.example.schedule.entity.User;

import java.time.LocalDateTime;


@Getter
public class ScheduleGetResponse { //단건 일정 조회 API에서 반환하는 응답 DTO
    private final Long scheduleId;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleGetResponse(Long scheduleId, String userName, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.scheduleId = scheduleId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
