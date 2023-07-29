package com.swger.tddstudy.user.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swger.tddstudy.user.service.dto.request.SignUpServiceRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @Size(min = 6, max = 12)
    @NotBlank
    private final String username;

    @Size(min = 6, max = 12)
    @NotBlank
    private final String password;

    @Size(min = 6, max = 12)
    @NotBlank
    private final String rePassword;

    @Size(min = 2, max = 10)
    @NotBlank
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
