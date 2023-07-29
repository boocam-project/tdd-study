package com.swger.tddstudy.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.swger.tddstudy.user.controller.dto.request.SignUpRequest;
import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.service.UserService;
import com.swger.tddstudy.user.service.dto.request.SignUpServiceRequest;
import com.swger.tddstudy.user.service.dto.response.SignUpResponse;
import com.swger.tddstudy.util.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class UserControllerTest extends ControllerTestSupport {

    private final UserService userService = mock(UserService.class);

    @DisplayName("signUp()은 ")
    @Nested
    class Context_SignUp {

        @DisplayName("회원가입을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            User newUser = User.createNewUser("user11", "password", "테스트유저1");
            given(userService.signUp(any(SignUpServiceRequest.class)))
                .willReturn(SignUpResponse.toDto(newUser));

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
    }
}
