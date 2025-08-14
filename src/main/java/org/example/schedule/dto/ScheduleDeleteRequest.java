package org.example.schedule.dto;

import jakarta.validation.constraints.NotNull;

public class ScheduleDeleteRequest {

    @NotNull(message = "password는 필수입니다")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
