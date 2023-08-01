package com.swger.tddstudy.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swger.tddstudy.user.domain.UserVO;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.request.LoginRequest;
import com.swger.tddstudy.user.restController.UserRestController;
import com.swger.tddstudy.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @DisplayName("회원가입 테스트 (양식 맞음)")
    void joinTest1() throws Exception {

        JoinRequest joinRequest = new JoinRequest("abc", "abcd1234!", "nickname");
        UserVO UserVO = new UserVO(0L, "abc", "abcd1234!", "nickname", "BRONZE", "USER");

        given(userService.save(joinRequest)).willReturn(
                UserVO
        );

        String json = new ObjectMapper().writeValueAsString(joinRequest);

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

        verify(userService).save(joinRequest);
    }

    @Test
    @DisplayName("회원가입 테스트 (양식 틀림)")
    void joinTest2() throws Exception {
        JoinRequest joinRequest = new JoinRequest("abc", "abc123", "nickname");
        UserVO UserVO = new UserVO(0L, "abc", "abc123", "nickname", "BRONZE", "USER");

        given(userService.save(joinRequest)).willReturn(
                UserVO
        );

        String json = new ObjectMapper().writeValueAsString(joinRequest);

        mockMvc.perform(post("/api/user/join")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists())
                .andDo(print());
    }


    @Test
    @DisplayName("로그인 테스트")
    void loginTest1() throws Exception {
        UserVO userVO = new UserVO(0L, "abc", "abcd1234!", "nickname", "BRONZE", "USER");

        LoginRequest loginRequest = LoginRequest.builder().username("abc").password("abcd1234!").build();

        given(userService.login(loginRequest)).willReturn(
                userVO
        );

        String json = new ObjectMapper().writeValueAsString(loginRequest);

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

        verify(userService).login(loginRequest);
    }
}
