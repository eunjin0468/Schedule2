package org.example.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequest { //새로운 사용자 정보를 서버에 저장 요청
    private String userName;
    private String password;
    private String email;

    public UserSaveRequest(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}
