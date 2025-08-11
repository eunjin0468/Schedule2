package org.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleSaveResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createTime;
    private final LocalDateTime modifiedAt;

    public ScheduleSaveResponse(Long id, String title, String content, String author, LocalDateTime createTime, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createTime = createTime;
        this.modifiedAt = modifiedAt;
    }
}
