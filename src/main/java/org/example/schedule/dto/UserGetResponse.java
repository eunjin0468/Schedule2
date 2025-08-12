package org.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserGetResponse { //단일 사용자 정보를 클라이언트에게 응답
    private final Long userId;
    private final String userName;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserGetResponse(Long userId, String userName, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
