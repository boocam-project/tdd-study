package com.swger.tddstudy.member;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.swger.tddstudy.member.domain.DTO.MemberDTO;
import com.swger.tddstudy.member.domain.DTO.MemberSignInDTO;
import com.swger.tddstudy.member.domain.Member;
import com.swger.tddstudy.member.domain.MemberLevel;
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
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원가입 성공")
    @Test
    public void SignUpSuccessInService(){
        //given
        MemberDTO memberDTO = new MemberDTO("testUsername",
                "testPassword", "testNickname", "testPassword");
        //when
        Member member = memberService.SignUp(memberDTO);
        Member memberInRepos = memberRepository.findById(member.getId()).get();
        //then
        Assertions.assertThat(member).extracting(
                        "id", "username", "nickname", "memberLevel", "memberType")
                .containsExactly(memberInRepos.getId(),
                        memberInRepos.getUsername(),
                        memberInRepos.getNickname(),
                        memberInRepos.getMemberLevel(),
                        memberInRepos.getMemberType());
    }
    @DisplayName("회원가입 실패-비밀번호 재확인")
    @Test
    public void SignUpFailRePassword(){
        //given
        MemberDTO memberDTO = new MemberDTO("testUsername",
                "testPassword", "testNickname", "test");
        //when
        //then
        Assertions.assertThatThrownBy(() -> memberService.SignUp(memberDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("RePassword Mismatch");
    }
    @DisplayName("로그인 성공")
    @Test
    public void SignInSuccessInService(){
        //given
        Member member = memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        MemberSignInDTO signInMemberDTO= new MemberSignInDTO("testUsername","testPassword");
        //when
        Member signInMember = memberService.SignIn(signInMemberDTO);
        //then
        Assertions.assertThat(member).extracting(
                        "id", "username", "nickname", "memberLevel", "memberType")
                .containsExactly(signInMember.getId(),
                        signInMember.getUsername(),
                        signInMember.getNickname(),
                        signInMember.getMemberLevel(),
                        signInMember.getMemberType());
    }
    @DisplayName("로그인 실패 - 아이디 틀림")
    @Test
    public void SignInFailUsername(){
        //given
        Member member = memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        MemberSignInDTO signInMemberDTO= new MemberSignInDTO("test","testPassword");
        //when
        //then
        Assertions.assertThatThrownBy(() -> memberService.SignIn(signInMemberDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username Mismatch");
    }
    @DisplayName("로그인 실패 - 비밀번호 틀림")
    @Test
    public void SignInFailPassword(){
        //given
        Member member = memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        MemberSignInDTO signInMemberDTO= new MemberSignInDTO("testUsername","test");
        //when
        //then
        Assertions.assertThatThrownBy(() -> memberService.SignIn(signInMemberDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password Mismatch");
    }
}
