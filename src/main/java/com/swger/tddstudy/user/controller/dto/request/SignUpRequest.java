package com.swger.tddstudy.user.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swger.tddstudy.user.service.dto.request.SignUpServiceRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6, max = 12, message = "아이디는 6 ~ 12 자리여야 합니다.")
    private final String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 12, message = "비밀번호는 6 ~ 12 자리여야 합니다.")
    private final String password;

    @NotBlank(message = "비밀번호 재확인을 입력해주세요.")
    @Size(min = 6, max = 12, message = "비밀번호 재확인은 6 ~ 12 자리여야 합니다.")
    private final String rePassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2 ~ 10 자리여야 합니다.")
    private final String nickname;

    @JsonCreator
    public SignUpRequest(@JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("rePassword") String rePassword,
        @JsonProperty("nickname") String nickname) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.nickname = nickname;
    }

    public SignUpServiceRequest toServiceDto() {
        return new SignUpServiceRequest(this.username, this.password, this.rePassword, this.nickname);
    }
}
