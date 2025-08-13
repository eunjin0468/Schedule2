package org.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSaveResponse { //사용자 생성 후, 생성된 사용자 정보나 상태 메시지를 클라이언트에게 전달
    private final Long userId;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserSaveResponse(Long userId, String userName, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public UserSaveResponse(Long userId) {
        this.userId = userId;
    }
}
