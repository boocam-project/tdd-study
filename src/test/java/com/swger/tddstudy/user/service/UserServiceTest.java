package com.swger.tddstudy.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserDto;
import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserType;
import com.swger.tddstudy.user.exception.LoginFailureException;
import com.swger.tddstudy.user.repository.UserRepository;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.request.LoginRequest;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Transactional
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("회원가입 서비스 : ")
    class JoinServiceTest {

        private JoinRequest newJoinRequest() {

            return JoinRequest.builder().username("abc").password("abcd1234!").nickname("nickname")
                .build();
        }

        private User newUser() {

            return User.builder().id(0L).username("abc").password("abcd1234!").nickname("nickname")
                .userLevel(UserLevel.BRONZE).type(UserType.USER).build();
        }

        private User newAdmin() {

            return User.builder().id(0L).username("abc").password("abcd1234!").nickname("nickname")
                .userLevel(UserLevel.BRONZE).type(UserType.ADMIN).build();
        }

        @Test
        @DisplayName("일반회원 회원가입 성공")
        void Test1() {

            // given
            given(userRepository.save(any(User.class))).willReturn(newUser());

            // when
            UserDto savedUserDto = userService.save(newJoinRequest());

            // then
            assertThat(savedUserDto).extracting("username", "password", "nickname", "userLevel",
                "type").containsExactly("abc", "abcd1234!", "nickname", "BRONZE", "USER");

            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("관리자 회원가입 성공")
        void Test2() {

            // given
            given(userRepository.save(any(User.class))).willReturn(newAdmin());

            // when
            UserDto savedUserDto = userService.saveAdmin(newJoinRequest());

            // then
            assertThat(savedUserDto).extracting("username", "password", "nickname", "userLevel",
                "type").containsExactly("abc", "abcd1234!", "nickname", "BRONZE", "ADMIN");

            verify(userRepository, times(1)).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("로그인 서비스 : ")
    class LoginServiceTest {

        private User newUser() {

            return User.builder().id(0L).username("abc").password("abcd1234!").nickname("nickname")
                .userLevel(UserLevel.BRONZE).type(UserType.USER).build();
        }

        private LoginRequest newLoginRequest() {

            return LoginRequest.builder().username("abc").password("abcd1234!").build();
        }

        private LoginRequest newWrongLoginRequest() {

            return LoginRequest.builder().username("abc").password("abcd5678!").build();
        }

        @Test
        @DisplayName("이름에 해당하는 회원이 있고, 비밀번호까지 일치하는 경우 성공")
        void Test1() {

            // given
            Optional<User> optionalUser = Optional.ofNullable(newUser());
            given(userRepository.findByUsername(any(String.class))).willReturn(optionalUser);

            // when
            UserDto loginResult = userService.login(newLoginRequest());

            // then
            assertThat(loginResult).extracting("username", "password", "nickname", "userLevel",
                "type").containsExactly("abc", "abcd1234!", "nickname", "BRONZE", "USER");

            verify(userRepository, times(1)).findByUsername(any(String.class));
        }

        @Test
        @DisplayName("이름이 일치하는 회원이 없을 경우 실패")
        void Test2() {

            // given
            given(userRepository.findByUsername(any(String.class))).willReturn(Optional.empty());

            // when, then
            Throwable exception = assertThrows(LoginFailureException.class, () -> {
                userService.login(newLoginRequest());
            });
            assertEquals("일치하는 회원정보가 없습니다.", exception.getMessage());
            verify(userRepository, times(1)).findByUsername(any(String.class));
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않는 경우 실패")
        void Test3() {

            // given
            Optional<User> optionalUser = Optional.ofNullable(newUser());
            given(userRepository.findByUsername(any(String.class))).willReturn(optionalUser);

            // when, then
            Throwable exception = assertThrows(LoginFailureException.class, () -> {
                userService.login(newWrongLoginRequest());
            });
            assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
            verify(userRepository, times(1)).findByUsername(any(String.class));
        }
    }

    @Nested
    @DisplayName("레벨업 서비스 : ")
    class LevelUpServiceTest {

        private User newUserBronze() {

            return User.builder().id(0L).username("abc").password("abcd1234!").nickname("nickname")
                .userLevel(UserLevel.BRONZE).type(UserType.USER).build();
        }

        private User newUserSilver() {

            return User.builder().id(0L).username("abc").password("abcd1234!").nickname("nickname")
                .userLevel(UserLevel.SILVER).type(UserType.USER).build();
        }

        private User newUserGold() {

            return User.builder().id(0L).username("abc").password("abcd1234!").nickname("nickname")
                .userLevel(UserLevel.GOLD).type(UserType.USER).build();
        }

        @Test
        @DisplayName("BRONZE -> SILVER")
        void Test1() {

            // given
            User user = newUserBronze();

            // when
            user.levelUp();

            // then
            assertThat(user.getUserLevel()).isEqualTo(UserLevel.SILVER);
        }

        @Test
        @DisplayName("SILVER -> GOLD")
        void Test2() {

            // given
            User user = newUserSilver();

            // when
            user.levelUp();

            // then
            assertThat(user.getUserLevel()).isEqualTo(UserLevel.GOLD);
        }

        @Test
        @DisplayName("GOLD -> GOLD")
        void Test3() {

            // given
            User user = newUserGold();

            // when
            user.levelUp();

            // then
            assertThat(user.getUserLevel()).isEqualTo(UserLevel.GOLD);
        }
    }
}