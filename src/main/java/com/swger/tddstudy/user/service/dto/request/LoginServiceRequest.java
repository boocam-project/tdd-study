package com.swger.tddstudy.user.service.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginServiceRequest {

    private final String username;
    private final String password;
}
