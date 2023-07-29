package com.swger.tddstudy.user.controller.dto.request;

import com.swger.tddstudy.user.service.dto.request.LoginServiceRequest;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequest {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    public LoginServiceRequest toServiceDto() {
        return new LoginServiceRequest(username, password);
    }
}
