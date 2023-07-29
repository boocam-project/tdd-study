package com.swger.tddstudy.user.service;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.repository.UserRepository;
import com.swger.tddstudy.user.service.dto.request.LoginServiceRequest;
import com.swger.tddstudy.user.service.dto.request.SignUpServiceRequest;
import com.swger.tddstudy.user.service.dto.response.LoginResponse;
import com.swger.tddstudy.user.service.dto.response.SignUpResponse;
import com.swger.tddstudy.util.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("signUp()는")
    @Nested
    class Context_signUp {

        @DisplayName("신규 유저를 생성할 수 있다.")
        @Test
        void _willSuccess(){
            // given
            SignUpServiceRequest signUpServiceRequest = new
                SignUpServiceRequest("username1", "1234", "1234", "nickname");

            // when
            SignUpResponse result = userService.signUp(signUpServiceRequest);

            User queriedUser = userRepository.findById(result.getId()).get();

            // then
            Assertions.assertThat(result)
                .extracting(
                    "id", "username", "nickname", "userLevel", "userType"
                )
                .containsExactly(queriedUser.getId(), queriedUser.getUsername(), queriedUser.getNickname(),
                    queriedUser.getUserLevel(), queriedUser.getType());
        }

        @DisplayName("Password와 Re-Password가 일치하지 않으면 실패한다.")
        @Test
        void notMatchedRePassword_willFail(){
            // given
            SignUpServiceRequest signUpServiceRequest = new
                SignUpServiceRequest("username1", "1234", "12345", "nickname");

            // when then
            Assertions.assertThatThrownBy(
                () -> userService.signUp(signUpServiceRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Re-Password Not Match");
        }
    }

    @DisplayName("login()는")
    @Nested
    class Context_Login {

        @DisplayName("로그인을 할 수 있다.")
        @Test
        void _willSuccess(){
            // given
            SignUpServiceRequest signUpServiceRequest = new
                SignUpServiceRequest("username1", "1234", "1234", "nickname");
            SignUpResponse signUpResult = userService.signUp(signUpServiceRequest);
            User queriedUser = userRepository.findById(signUpResult.getId()).get();

            // when
            LoginServiceRequest loginServiceRequest = new LoginServiceRequest("username1", "1234");
            LoginResponse loginResult = userService.login(loginServiceRequest);

            // then
            Assertions.assertThat(loginResult)
                .extracting(
                    "id", "username", "nickname", "userLevel", "userType"
                )
                .containsExactly(queriedUser.getId(), queriedUser.getUsername(), queriedUser.getNickname(), queriedUser.getUserLevel(), queriedUser.getType());
        }

        @DisplayName("아이디가 일치하지 않으면 실패한다.")
        @Test
        void notMatchUsername_willFail(){
            // given
            SignUpServiceRequest signUpServiceRequest = new
                SignUpServiceRequest("username1", "1234", "1234", "nickname");
            userService.signUp(signUpServiceRequest);

            // when then
            LoginServiceRequest loginServiceRequest = new LoginServiceRequest("username2", "12344");
            Assertions.assertThatThrownBy(
                    () -> userService.login(loginServiceRequest))
                .isInstanceOf(BadCredentialException.class)
                .hasMessage("적절하지 않은 로그인 정보입니다.");
        }

        @DisplayName("패스워드가 일치하지 않으면 실패한다.")
        @Test
        void notMatchedPassword_willFail(){
            // given
            SignUpServiceRequest signUpServiceRequest = new
                SignUpServiceRequest("username1", "1234", "1234", "nickname");
            userService.signUp(signUpServiceRequest);

            // when then
            LoginServiceRequest loginServiceRequest = new LoginServiceRequest("username1", "12345");
            Assertions.assertThatThrownBy(
                    () -> userService.login(loginServiceRequest))
                .isInstanceOf(BadCredentialException.class)
                .hasMessage("적절하지 않은 로그인 정보입니다.");
        }
    }
}
