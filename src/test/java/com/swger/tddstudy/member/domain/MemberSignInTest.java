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
class MemberSignInTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    /**
     * 로그인
     * */
    /* 로그인 성공 */
    /* 사용자 입력: 아이디:testUsername , 비밀번호: testPassword*/
    @Test
    public void SignInSuccess() throws Exception {
        //given
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
    /* 로그인 실패 - 아이디 틀림 */
    /* 사용자 입력: 아이디:test , 비밀번호: testPassword*/
    @Test
    public void SignInFailUsername() throws Exception {
        //given
        memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        MemberSignInDTO signInMember= new MemberSignInDTO("test","testPassword");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/logIn")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Username MisMatch"))
                .andDo(print());
    }
    /* 로그인 실패 - 비밀번호 틀림 */
    /* 사용자 입력: 아이디:testUsername , 비밀번호: test*/
    @Test
    public void SignInFailPassword() throws Exception {
        //given
        memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        MemberSignInDTO signInMember= new MemberSignInDTO("testUsername","test");
        String content = om.writeValueAsString(signInMember);
        mockMvc.perform(post("/logIn")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Password MisMatch"))
                .andDo(print());
    }

}