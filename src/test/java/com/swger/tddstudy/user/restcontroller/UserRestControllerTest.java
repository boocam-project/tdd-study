package com.swger.tddstudy.user.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swger.tddstudy.user.domain.UserDto;
import com.swger.tddstudy.user.exception.LoginFailureException;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.request.LoginRequest;
import com.swger.tddstudy.user.restcontroller.UserRestController;
import com.swger.tddstudy.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Nested
    @DisplayName("회원가입 컨트롤러 : ")
    class JoinControllerTest {

        private JoinRequest newJoinRequest() {

            return JoinRequest.builder().username("abc").password("abcd1234!").nickname("nickname")
                .build();
        }

        private JoinRequest newWrongJoinRequest() {

            return JoinRequest.builder().username("abc").password("abc123").nickname("nickname")
                .build();
        }

        private UserDto newUserDto() {

            return UserDto.builder().id(0L).username("abc").password("abcd1234!")
                .nickname("nickname").userLevel("BRONZE").type("USER").build();
        }

        private UserDto newWrongUserDto() {

            return UserDto.builder().id(0L).username("abc").password("abc123")
                .nickname("nickname").userLevel("BRONZE").type("USER").build();
        }

        @Test
        @DisplayName("양식에 맞을 경우 성공")
        void Test1() throws Exception {

            // given
            given(userService.save(any(JoinRequest.class))).willReturn(
                newUserDto()
            );
            String json = new ObjectMapper().writeValueAsString(newJoinRequest());

            // when, then
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
            verify(userService, times(1)).save(any(JoinRequest.class));
        }

        @Test
        @DisplayName("양식에 맞지 않을 경우 실패")
        void Test2() throws Exception {

            // given
            given(userService.save(any(JoinRequest.class))).willReturn(
                newWrongUserDto()
            );
            String json = new ObjectMapper().writeValueAsString(newWrongJoinRequest());

            // when, then
            mockMvc.perform(post("/api/user/join")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists())
                .andDo(print());
            verify(userService, never()).save(newJoinRequest());
        }
    }

    @Nested
    @DisplayName("로그인 컨트롤러 테스트 : ")
    class LoginControllerTest {

        private LoginRequest newLoginRequest() {

            return LoginRequest.builder().username("abc").password("abcd1234!").build();
        }

        private LoginRequest newWrongLoginRequest() {

            return LoginRequest.builder().username("abc").password("abc123").build();
        }

        private UserDto newUserDto() {

            return UserDto.builder().id(0L).username("abc").password("abcd1234!")
                .nickname("nickname").userLevel("BRONZE").type("USER").build();

        }

        @Test
        @DisplayName("양식에 맞을 경우 성공")
        void Test1() throws Exception {

            // given
            given(userService.login(any(LoginRequest.class))).willReturn(
                newUserDto()
            );
            String json = new ObjectMapper().writeValueAsString(newLoginRequest());

            // when, then
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
            verify(userService, times(1)).login(any(LoginRequest.class));
        }

        @Test
        @DisplayName("양식에 맞지 않을 경우 실패")
        void Test2() throws Exception {

            // given
            given(userService.login(any(LoginRequest.class))).willThrow(
                LoginFailureException.class);
            String json = new ObjectMapper().writeValueAsString(newWrongLoginRequest());

            // when, then
            mockMvc.perform(post("/api/user/login")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists())
                .andDo(print());
            verify(userService, never()).login(any(LoginRequest.class));
        }
    }
}
