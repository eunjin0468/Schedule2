package org.example.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class ScheduleSaveRequest {
    //id는 디비에서 생성
    private String title;
    private String content;
    private String author;
    private String password;
}


//내부적으로 reflection써서 강제로 데이터 매핑 ->세터, 파라미터를 포함한 생성자 필요 없음
