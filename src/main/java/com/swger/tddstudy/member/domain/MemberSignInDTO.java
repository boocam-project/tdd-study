package com.swger.tddstudy.member.domain;

import lombok.Getter;

@Getter
public class MemberSignInDTO {

    private String username;
    private String password;

    public MemberSignInDTO(String username, String password) {
        this.username = username;
        this.password = password;

    }
}
