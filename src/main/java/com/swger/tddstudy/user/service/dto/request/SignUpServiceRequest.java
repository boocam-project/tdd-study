package com.swger.tddstudy.user.service.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpServiceRequest {

    private final String username;
    private final String password;
    private final String rePassword;
    private final String nickname;
}
