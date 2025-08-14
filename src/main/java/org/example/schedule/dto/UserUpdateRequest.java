package org.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest { //사용자가 변경하고 싶은 정보들을 담아서 서버에 전달
    //userName, email 수정

    @NotBlank(message = "userName은 필수입니다.")
    private String userName;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다. 예: example@domain.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min=8, max=20, message = "비밀번호는 8자 이상 20자 이하이며, 영문자, 숫자, 특수문자를 모두 포함해야 합니다.")
    @Pattern(regexp = ".*[A-Z].*", message = "비밀번호는 최소 하나 이상의 대문자를 포함해야 합니다.")
    @Pattern(regexp = ".*[a-z].*", message = "비밀번호는 최소 하나 이상의 소문자를 포함해야 합니다.")
    @Pattern(regexp = ".*[0-9].*", message = "비밀번호는 최소 하나 이상의 숫자를 포함해야 합니다.")
    @Pattern(regexp = ".*[!@#$%^&*].*", message = "비밀번호는 최소 하나 이상의 특수문자를 포함해야 합니다.")
    private String password;
}
