package com.swger.tddstudy.user.service.dto.response;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponse {

    private final Long id;
    private final String username;
    private final String nickname;
    private final UserLevel userLevel;
    private final UserType userType;

    public static LoginResponse toDto(User user) {
        return new LoginResponse(user.getId(), user.getUsername(), user.getNickname(),
            user.getUserLevel(), user.getType());
    }
}
