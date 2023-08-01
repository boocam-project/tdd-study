package com.swger.tddstudy.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swger.tddstudy.user.domain.UserVO;
import com.swger.tddstudy.user.restController.UserRestController;
import com.swger.tddstudy.user.service.UserRegex;
import com.swger.tddstudy.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRegex userRegex;

    // 회원가입 정규식 확인 결과 HashMap 객체 (True)
    public Map<String, Object> newRegexTestTrue() {
        Map<String, Object> regexTest = new HashMap<>();
        regexTest.put("result", true);
        //regexTest.put("message", "양식 안맞음");
        return regexTest;
    }

    // 회원가입 정규식 확인 결과 HashMap 객체 (False)
    public Map<String, Object> newRegexTestFalse() {
        Map<String, Object> regexTest = new HashMap<>();
        regexTest.put("result", false);
        regexTest.put("message", "양식에 대한 설명");
        return regexTest;
    }

    @Test
    @DisplayName("회원가입 테스트 (양식 맞음)")
    void joinTest1() throws Exception {

        UserVO userVO = new UserVO("abc", "abcd1234!", "nickname", "BRONZE", "USER");
        UserVO savedUserVO = userVO;
        savedUserVO.setId(0L);

        given(userService.save(userVO)).willReturn(
                savedUserVO
        );

        given(userRegex.isJoinRegex(userVO)).willReturn(
                newRegexTestTrue()
        );

        String json = new ObjectMapper().writeValueAsString(userVO);

        mockMvc.perform(post("/api/user/join")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.username").exists())
                .andExpect(jsonPath("$.data.password").exists())
                .andExpect(jsonPath("$.data.nickname").exists())
                .andExpect(jsonPath("$.data.userLevel").exists())
                .andExpect(jsonPath("$.data.type").exists())
                .andDo(print());

        verify(userService).save(userVO);

    }

    @Test
    @DisplayName("회원가입 테스트 (양식 틀림)")
    void joinTest2() throws Exception {

        UserVO userVO = new UserVO("abc", "abc123", "nickname", "BRONZE", "USER");
        UserVO savedUserVO = userVO;
        savedUserVO.setId(0L);

        given(userService.save(userVO)).willReturn(
                savedUserVO
        );

        given(userRegex.isJoinRegex(userVO)).willReturn(
                newRegexTestFalse()
        );

        String json = new ObjectMapper().writeValueAsString(userVO);

        mockMvc.perform(post("/api/user/join")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andDo(print());

        verify(userRegex).isJoinRegex(userVO);

    }


    @Test
    @DisplayName("로그인 테스트 (이름, 비밀번호 일치)")
    void loginTest1() throws Exception {
        UserVO userVO = new UserVO(0L, "abc", "abcd1234!", "nickname", "BRONZE", "USER");

        UserVO loginUser = new UserVO();
        loginUser.setUsername("abc");
        loginUser.setPassword("abcd1234!");

        given(userService.login(loginUser)).willReturn(
                userVO
        );

        String json = new ObjectMapper().writeValueAsString(loginUser);

        mockMvc.perform(post("/api/user/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.username").exists())
                .andExpect(jsonPath("$.data.password").exists())
                .andExpect(jsonPath("$.data.nickname").exists())
                .andExpect(jsonPath("$.data.userLevel").exists())
                .andExpect(jsonPath("$.data.type").exists())
                .andDo(print());

        verify(userService).login(loginUser);

    }

    @Test
    @DisplayName("로그인 테스트 (이름, 비밀번호 불일치)")
    void loginTest2() throws Exception {

        UserVO loginUser = new UserVO();
        loginUser.setUsername("테스트용 이름1");
        loginUser.setPassword("테스트용 비밀번호2");

        given(userService.login(loginUser)).willReturn(
                null
        );

        String json = new ObjectMapper().writeValueAsString(loginUser);

        mockMvc.perform(post("/api/user/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").exists())
                .andDo(print());

        verify(userService).login(loginUser);

    }
}
