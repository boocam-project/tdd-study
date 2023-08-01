package com.swger.tddstudy.member.domain;

import com.swger.tddstudy.member.domain.DTO.MemberDTO;
import com.swger.tddstudy.util.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
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
    private MemberLevel memberLevel;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.memberLevel = MemberLevel.BRONZE;
        this.memberType = MemberType.Member;
    }
    public Member(){}

    public Member(MemberDTO memberDTO){
        this.username = memberDTO.getUsername();
        this.password = memberDTO.getPassword();
        this.nickname = memberDTO.getNickname();
        this.memberLevel = MemberLevel.BRONZE;
        this.memberType = MemberType.Member;
    }
    public Member AdminMember(MemberDTO memberDTO){
        Member member = new Member(memberDTO);
        member.memberType = MemberType.ADMIN;
        return member;
    }

}
