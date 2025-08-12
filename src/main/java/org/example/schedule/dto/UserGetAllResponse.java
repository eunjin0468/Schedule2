package org.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserGetAllResponse {
    private final Long userId;
    private final String userName;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<ScheduleSimpleResponse> schedules;

    public UserGetAllResponse(Long userId, String userName, String email, LocalDateTime createdAt, LocalDateTime modifiedAt, List<ScheduleSimpleResponse> schedules) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.schedules = schedules;
    }
}
