package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest { //사용자가 변경하고 싶은 정보들을 담아서 서버에 전달
    //userName, email 수정
    private String userName;
    private String email;
}
