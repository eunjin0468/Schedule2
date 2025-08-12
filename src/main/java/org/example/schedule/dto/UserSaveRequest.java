package org.example.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequest { //새로운 사용자 정보를 서버에 저장 요청
    private String userName;
    private String email;
}
