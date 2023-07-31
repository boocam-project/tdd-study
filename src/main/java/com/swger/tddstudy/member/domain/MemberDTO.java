package com.swger.tddstudy.member.domain;

import lombok.Getter;

@Getter
public class MemberDTO {

    private String username;
    private String password;
    private String nickname;
    private String rePassword;

    public MemberDTO(String username, String password, String nickname, String rePassword) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.rePassword = rePassword;
    }
}
