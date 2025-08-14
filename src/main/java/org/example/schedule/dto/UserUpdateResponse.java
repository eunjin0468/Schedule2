package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class UserUpdateResponse { //사용자 정보 수정 요청을 보낸 뒤, 서버가 수정 결과를 응답
    private final String userName;
    private final String email;
    private final String message;

    public UserUpdateResponse(String userName, String email,  String message) {
        this.userName = userName;
        this.email = email;
        this.message = message;
    }
}
