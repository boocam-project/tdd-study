package com.swger.tddstudy.user;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserType;
import com.swger.tddstudy.user.domain.UserVO;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.request.LoginRequest;
import com.swger.tddstudy.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    private UserService userService;

    public UserVO newUser() {
        return new UserVO("abc", "abcd1234!", "nickname", "BRONZE", "USER");
    }

    @Test
    @Rollback(value = true)
    @DisplayName("회원가입 테스트 (USER)")
    public void userJoinTest1() {
        JoinRequest joinRequest = new JoinRequest("abc", "abcd1234!", "nickname");
        UserVO savedUserVO = userService.save(joinRequest);
        assertThat(savedUserVO).extracting("username", "password", "nickname", "userLevel", "type")
                .containsExactly("abc", "abcd1234!", "nickname", "BRONZE", "USER");
    }

    @Test
    @Rollback(value = true)
    @DisplayName("회원가입 테스트 (ADMIN)")
    public void userJoinTest2() {
        JoinRequest joinRequest = new JoinRequest("abc", "abcd1234!", "nickname");
        UserVO savedUserVO = userService.saveAdmin(joinRequest);
        assertThat(savedUserVO).extracting("username", "password", "nickname", "userLevel", "type")
                .containsExactly("abc", "abcd1234!", "nickname", "BRONZE", "ADMIN");
    }

    @Test
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void userloginTest1() {
        JoinRequest joinRequest = new JoinRequest("abc", "abcd1234!", "nickname");
        userService.save(joinRequest);
        // 로그인 객체 생성 후 로그인
        LoginRequest loginRequest = LoginRequest.builder().username("abc").password("abcd1234!").build();
        UserVO loginResult = userService.login(loginRequest);
        // 로그인 결과가 UserVO면 성공
        assertThat(loginResult).extracting("username", "password", "nickname", "userLevel", "type")
                .containsExactly("abc", "abcd1234!", "nickname", "BRONZE", "USER");
    }

    @Test
    @Rollback(value = true)
    @DisplayName("레벨업 테스트 (BRONZE -> SILVER)")
    public void levelUpTest1() {
        User user = User.builder().id(0L).username("abc").password("abcd1234!").nickname("abcd").userLevel(UserLevel.BRONZE).type(UserType.USER).build();
        user.levelUp();
        assertThat(user.getUserLevel()).isEqualTo(UserLevel.SILVER);
    }

    @Test
    @Rollback(value = true)
    @DisplayName("레벨업 테스트 (SILVER -> GOLD)")
    public void levelUpTest2() {
        User user = User.builder().id(0L).username("abc").password("abcd1234!").nickname("abcd").userLevel(UserLevel.SILVER).type(UserType.USER).build();
        user.levelUp();
        assertThat(user.getUserLevel()).isEqualTo(UserLevel.GOLD);
    }

    @Test
    @Rollback(value = true)
    @DisplayName("레벨업 테스트 (GOLD -> GOLD)")
    public void levelUpTest3() {
        User user = User.builder().id(0L).username("abc").password("abcd1234!").nickname("abcd").userLevel(UserLevel.GOLD).type(UserType.USER).build();
        user.levelUp();
        assertThat(user.getUserLevel()).isEqualTo(UserLevel.GOLD);
    }

}
