package com.swger.tddstudy.member.domain;


import com.swger.tddstudy.member.repository.MemberRepository;
import com.swger.tddstudy.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class MemberTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    /**
     * 회원가입
     * */
    /* 회원 가입 성공 */
    /* 사용자 입력: 아이디:testMembername , 비밀번호: testPassword , 닉네임: testNickname*/
    @Test
    public void SignUpSuccess() {
        //given
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUsername("testUsername");
        memberDTO.setPassword("testPassword");
        memberDTO.setNickname("testNickname");
        //when
        Member member= new Member(memberDTO.getUsername(), memberDTO.getPassword(), memberDTO.getNickname());
        memberService.join(member);
        List<Member> getMember = memberRepository.findByUsername("testUsername");
        //then
        Assertions.assertThat(getMember.get(0)).isEqualTo(member);
    }

    /* 회원 가입 실패 - 아이디 */
    /* 사용자 입력: 아이디: , 비밀번호: testPassword , 닉네임: testNickname*/
    @Test
    public void SignUpUsernameBlank() {
        //given
        MemberDTO MemberDTO = new MemberDTO();
        MemberDTO.setPassword("testPassword");
        MemberDTO.setNickname("testNickname");
        //when
        Member Member = new Member(MemberDTO.getUsername(), MemberDTO.getPassword(), MemberDTO.getNickname());
        memberService.join(Member);
        List<Member> getMember = memberRepository.findByUsername("");
        //then
        Assertions.assertThat(getMember.get(0)).isEqualTo(Member);
    }
    /* 회원 가입 실패 - 비밀번호*/

    /* 회원 가입 실패 - 비밀번호 재확인*/

    /* 회원 가입 실패 - 닉네임 */
}