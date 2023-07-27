package com.swger.tddstudy.member.domain;

import com.swger.tddstudy.util.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberLevel MemberLevel;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
