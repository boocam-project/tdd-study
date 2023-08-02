package com.swger.tddstudy.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.swger.tddstudy.user.controller.dto.request.LoginRequest;
import com.swger.tddstudy.user.controller.dto.request.SignUpRequest;
import com.swger.tddstudy.util.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class UserControllerTest extends ControllerTestSupport {

    @DisplayName("signUp()은 ")
    @Nested
    class Context_SignUp {

        @DisplayName("회원가입을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            SignUpRequest requestDto = new SignUpRequest("user11", "password",
                "password", "테스트유저1");

            // when then
            mockMvc.perform(
                    post("/api/v1/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
        }

        @DisplayName("username 은 ")
        @Nested
        class Element_username {

            @DisplayName("제약조건을 만족하면 성공한다.")
            @Test
            void _willSuccess() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
            }

            @DisplayName("null이면 실패한다.")
            @Test
            void null_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest(null, "password",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("아이디를 입력해주세요."));
            }

            @DisplayName("공백은 허용하지 않는다.")
            @Test
            void blank_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("       ", "password",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("아이디를 입력해주세요."));
            }

            @DisplayName("길이가 6보다 커야 한다.")
            @Test
            void shorterThan6_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user1", "password",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("아이디는 6 ~ 12 자리여야 합니다."));
            }

            @DisplayName("길이가 12보다 작아야 한다.")
            @Test
            void longerThan12_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("longUsername1", "password",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("아이디는 6 ~ 12 자리여야 합니다."));
            }
        }

        @DisplayName("password 는 ")
        @Nested
        class Element_password {

            @DisplayName("제약사항을 만족하면 성공한다.")
            @Test
            void _willSuccess() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
            }

            @DisplayName("null이면 실패한다.")
            @Test
            void null_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", null,
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("비밀번호를 입력해주세요."));
            }

            @DisplayName("공백은 허용하지 않는다.")
            @Test
            void blank_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "        ",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("비밀번호를 입력해주세요."));
            }

            @DisplayName("길이가 6보다 커야 한다.")
            @Test
            void shorterThan6_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "pwd11",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("비밀번호는 6 ~ 12 자리여야 합니다."));
            }

            @DisplayName("길이가 12보다 작아야 한다.")
            @Test
            void longerThan12_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("username", "longPassword1",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("비밀번호는 6 ~ 12 자리여야 합니다."));
            }
        }

        @DisplayName("rePassword 는 ")
        @Nested
        class Element_rePassword {

            @DisplayName("제약사항을 만족하면 성공한다.")
            @Test
            void _willSuccess() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
            }

            @DisplayName("null이면 실패한다.")
            @Test
            void null_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    null, "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("비밀번호 재확인을 입력해주세요."));
            }

            @DisplayName("공백은 허용하지 않는다.")
            @Test
            void blank_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    "        ", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("비밀번호 재확인을 입력해주세요."));
            }

            @DisplayName("길이가 6보다 커야 한다.")
            @Test
            void shorterThan6_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user1", "password",
                    "pwd11", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("비밀번호 재확인은 6 ~ 12 자리여야 합니다."));
            }

            @DisplayName("길이가 12보다 작아야 한다.")
            @Test
            void longerThan12_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("username", "password",
                    "longPassword1", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("비밀번호 재확인은 6 ~ 12 자리여야 합니다."));
            }
        }

        @DisplayName("nickname 는 ")
        @Nested
        class Element_nickname {

            @DisplayName("제약사항을 만족하면 성공한다.")
            @Test
            void _willSuccess() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    "password", "테스트유저1");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
            }

            @DisplayName("null이면 실패한다.")
            @Test
            void null_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    "password", null);

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("닉네임을 입력해주세요."));
            }

            @DisplayName("공백은 허용하지 않는다.")
            @Test
            void blank_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    "password", "  ");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("닉네임을 입력해주세요."));
            }

            @DisplayName("길이가 2보다 커야 한다.")
            @Test
            void shorterThan2_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("user11", "password",
                    "password", "닉");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("닉네임은 2 ~ 10 자리여야 합니다."));
            }

            @DisplayName("길이가 10보다 작아야 한다.")
            @Test
            void longerThan12_willFail() throws Exception {
                // given
                SignUpRequest requestDto = new SignUpRequest("username", "password",
                    "password", "tooLongNick");

                // when then
                mockMvc.perform(
                        post("/api/v1/signUp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("닉네임은 2 ~ 10 자리여야 합니다."));
            }
        }
    }

    @DisplayName("login()은")
    @Nested
    class Context_Login {

        @DisplayName("로그인을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            LoginRequest loginRequest = new LoginRequest("user11", "password");

            // when then
            mockMvc.perform(post("/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk());
        }

        @DisplayName("username은 ")
        @Nested
        class Element_username {

            @DisplayName("null 이면 안된다.")
            @Test
            void null_willFail() throws Exception {
                // given
                LoginRequest loginRequest = new LoginRequest(null, "password");

                // when then
                mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }

            @DisplayName("공백은 허용하지 않는다.")
            @Test
            void blank_willFail() throws Exception {
                // given
                LoginRequest loginRequest = new LoginRequest(" ", "password");

                // when then
                mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }
        }

        @DisplayName("password은 ")
        @Nested
        class Element_password {

            @DisplayName("null 이면 안된다.")
            @Test
            void null_willFail() throws Exception {
                // given
                LoginRequest loginRequest = new LoginRequest("user11", null);

                // when then
                mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }

            @DisplayName("공백은 허용하지 않는다.")
            @Test
            void blank_willFail() throws Exception {
                // given
                LoginRequest loginRequest = new LoginRequest("user111", " ");

                // when then
                mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }
        }
    }
}
