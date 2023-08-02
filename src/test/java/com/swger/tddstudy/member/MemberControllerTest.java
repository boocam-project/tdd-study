package com.swger.tddstudy.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swger.tddstudy.member.domain.DTO.MemberDTO;
import com.swger.tddstudy.member.domain.DTO.MemberSignInDTO;
import com.swger.tddstudy.member.domain.Member;
import com.swger.tddstudy.member.repository.MemberRepository;
import com.swger.tddstudy.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;

import javax.transaction.Transactional;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;

    @DisplayName("회원 가입 성공")
    @Test
    public void SignUpSuccess() throws Exception {
        //given
        MemberDTO signInMember= new MemberDTO("testUsername",
                "testPassword", "testNickname", "testPassword");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/signUp")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("signUpOK"))
                .andDo(print());
        //when
        Optional<Member> getMember = memberRepository.findByUsername(signInMember.getUsername());
        //then
        /* 아이디, 비밀번호 같은지 확인*/
        Assertions.assertThat(getMember.get().getUsername()).isEqualTo(signInMember.getUsername());
        Assertions.assertThat(getMember.get().getPassword()).isEqualTo(signInMember.getPassword());
    }

    @DisplayName("회원가입 실패 - Validation 실패")
    @Test
    public void SignUpFail() throws Exception {
        MemberDTO signInMember= new MemberDTO("testUsername",
                "testPassword", null, "testPassword");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/signUp")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        .getClass().isAssignableFrom(BindException.class)))
                        .andDo(print());

    }
    @DisplayName("로그인 성공")
    @Test
    public void SignInSuccess() throws Exception {
        memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        MemberSignInDTO signInMember= new MemberSignInDTO("testUsername","testPassword");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/logIn")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("LogInOK"))
                .andDo(print());
    }
    @DisplayName("로그인 실패 - Vaildation 실패")
    @Test
    public void SignInFail() throws Exception {
        memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        MemberSignInDTO signInMember= new MemberSignInDTO(null,"testPassword");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/logIn")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(BindException.class)))
                .andDo(print());
    }

}
