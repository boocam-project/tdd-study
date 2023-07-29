package com.swger.tddstudy.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserType;
import com.swger.tddstudy.user.repository.UserRepository;
import com.swger.tddstudy.user.service.dto.request.SignUpServiceRequest;
import com.swger.tddstudy.user.service.dto.response.SignUpResponse;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
class MockUserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("signUp()는")
    @Nested
    class Context_SignUp {

        @DisplayName("신규 유저를 생성할 수 있다.")
        @Test
        void _willSuccess(){
            // given
            SignUpServiceRequest signUpServiceRequest = new
                SignUpServiceRequest("username1", "1234", "1234", "nickname");
            User createdUser = User.createNewUser("username1", "1234", "nickname");

            // when
            when(userRepository.save(any(User.class))).thenReturn(createdUser);
            SignUpResponse signUpResponse = userService.signUp(signUpServiceRequest);

            // then
            Assertions.assertThat(signUpResponse)
                .extracting(
                    "username", "nickname", "userLevel", "userType"
                )
                .containsExactly("username1", "nickname", UserLevel.BRONZE, UserType.USER);

            verify(userRepository, times(1)).save(any(User.class));
        }
    }
}
