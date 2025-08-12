package org.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserGetAllResponse { //사용자 정보를 클라이언트에게 응답
    private final long userId;
    private final String userName;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserGetAllResponse(Long userId, String userName, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
