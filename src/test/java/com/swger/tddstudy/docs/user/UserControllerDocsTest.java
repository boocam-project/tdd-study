package com.swger.tddstudy.docs.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.swger.tddstudy.docs.RestDocsSupport;
import com.swger.tddstudy.user.controller.UserController;
import com.swger.tddstudy.user.controller.dto.request.SignUpRequest;
import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserType;
import com.swger.tddstudy.user.service.UserService;
import com.swger.tddstudy.user.service.dto.request.SignUpServiceRequest;
import com.swger.tddstudy.user.service.dto.response.SignUpResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class UserControllerDocsTest extends RestDocsSupport {

    private final UserService userService = mock(UserService.class);

    @Override
    protected Object initController() {
        return new UserController(userService);
    }

    @DisplayName("회원가입을 할 수 있다.")
    @Test
    void _willSuccess() throws Exception {
        // given
        User newUser = User.createNewUser("user11", "password", "테스트유저1");
        given(userService.signUp(any(SignUpServiceRequest.class)))
            .willReturn(new SignUpResponse(1L, "user11", "테스트유저1", UserLevel.BRONZE, UserType.USER));

        SignUpRequest requestDto = new SignUpRequest("user11", "password",
            "password", "테스트유저1");

        // when then
        mockMvc.perform(
                post("/api/v1/signUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(print())
            .andDo(document("user-signUp",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("username").type(JsonFieldType.STRING).description("유저_아이디")
                        .attributes(key("constraints").value("Non_Blank")),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("유저_비밀번호")
                        .attributes(key("constraints").value("Non_Blank")),
                    fieldWithPath("rePassword").type(JsonFieldType.STRING)
                        .description("유저_비밀번호_재확인")
                        .attributes(key("constraints").value("Non_Blank")),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저_닉네임")
                        .attributes(key("constraints").value("Non_Blank"))
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저_식별자"),
                    fieldWithPath("username").type(JsonFieldType.STRING).description("유저_아이디"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저_닉네임"),
                    fieldWithPath("userLevel").type(JsonFieldType.STRING).description("유저_등급"),
                    fieldWithPath("userType").type(JsonFieldType.STRING).description("유저_타입")
                )
            ));
    }
}
