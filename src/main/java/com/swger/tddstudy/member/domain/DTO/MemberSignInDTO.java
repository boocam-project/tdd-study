package com.swger.tddstudy.member.domain.DTO;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberSignInDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public MemberSignInDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public MemberSignInDTO(){}
}
