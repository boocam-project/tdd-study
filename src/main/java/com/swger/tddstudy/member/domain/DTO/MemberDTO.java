package com.swger.tddstudy.member.domain.DTO;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    private String rePassword;

    public MemberDTO(String username, String password, String nickname, String rePassword) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.rePassword = rePassword;
    }
    public MemberDTO(){}
}
