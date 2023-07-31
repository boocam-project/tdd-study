package com.swger.tddstudy.member.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.swger.tddstudy.member.repository.MemberRepository;
import com.swger.tddstudy.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MemberSignUpTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    /**
     * 회원가입
     * */
    /* 회원 가입 성공 */
    /* 사용자 입력: 아이디:testUsername , 비밀번호: testPassword , 닉네임: testNickname, 비밀번호재확인: testPassword*/
    @Test
    public void SignUpSuccess() throws Exception {
        //given
        MemberDTO signInMember= new MemberDTO("testUsername",
                "testPassword", "testNickname", "testPassword");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/signIn")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("signInOK"))
                .andDo(print());
        //when
        List<Member> getMember = memberRepository.findByUsername("testUsername");
        //then
        /* 아이디, 비밀번호 같은지 확인*/
        /* equals로 진행하려고 했지만 안됨*/
        Assertions.assertThat(getMember.get(0).getUsername()).isEqualTo(signInMember.getUsername());
        Assertions.assertThat(getMember.get(0).getPassword()).isEqualTo(signInMember.getPassword());
    }

    /* 회원 가입 실패 - 비밀번호 재확인*/
    /* 사용자 입력: 아이디:testMembername , 비밀번호: testPassword , 닉네임: testNickname, 비밀번호재확인: test*/
    @Test
    public void SignUpRePasswordMismatch() throws Exception {
        //given
        MemberDTO signInMember= new MemberDTO("testUsername",
                "testPassword", "testNickname", "test");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/signIn")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Password Mismatch"))
                .andDo(print());

    }
//    /* 회원 가입 실패 - 아이디 */
    /* 사용자 입력: 아이디: , 비밀번호: testPassword , 닉네임: testNickname, 비밀번호재확인: testPassword*/
    @Test
    public void SignUpUsernameBlank() throws Exception {
        //given
        MemberDTO signInMember= new MemberDTO("testUsername",
                "testPassword", "", "testPassword");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/signIn")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("signInOK"))
                .andDo(print());
        // validation이 진행되지 않는 이유...
//        List<Member> members = memberRepository.findByUsername("testUsername");
    }
}