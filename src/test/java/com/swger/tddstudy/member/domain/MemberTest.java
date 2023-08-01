package com.swger.tddstudy.member.domain;

import com.swger.tddstudy.member.domain.DTO.MemberDTO;
import com.swger.tddstudy.member.repository.MemberRepository;
import com.swger.tddstudy.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("등급 업")
    @Test
    public void LevelUp(){
        //given
        Member member = memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        //when
        member.LevelUp();
        //then
        Assertions.assertThat(member.getMemberLevel()).isEqualTo(MemberLevel.SILVER);
    }
    @DisplayName("Admin 타입으로 회원가입")
    @Test
    public void SignUpAdmin(){
        //given
        MemberDTO signUpMember= new MemberDTO("testUsername",
                "testPassword", "testNickname", "testPassword");
        //when
        Member adminMember = memberService.SignUpAdmin(signUpMember);
        //then
        Assertions.assertThat(adminMember.getMemberType()).isEqualTo(MemberType.ADMIN);
    }
}
