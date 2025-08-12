package org.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleSaveResponse { //일정 생성 완료 후 서버가 클라이언트에 반환하는 응답 DTO
    private final Long scheduleId;
    private final String title;
    private final String content;
    private final LocalDateTime createTime;
    private final LocalDateTime modifiedAt;

    public ScheduleSaveResponse(Long scheduleId, String title, String content, LocalDateTime createTime, LocalDateTime modifiedAt) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.modifiedAt = modifiedAt;
    }
}
