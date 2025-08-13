package org.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleSaveRequest { //일정 등록 요청 시 클라이언트가 서버로 보내는 데이터 구조

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max=100, message = "제목은 100자 이내여야 합니다.")
    private String title;

    @Size(max=500, message = "내용은 500자 이내여야 합니다.")
    private String content;

    public ScheduleSaveRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
