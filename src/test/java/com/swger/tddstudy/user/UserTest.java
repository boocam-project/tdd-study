package com.swger.tddstudy.user;

import com.swger.tddstudy.user.domain.UserVO;
import com.swger.tddstudy.user.service.UserService;
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
        UserVO user = new UserVO("테스트용 이름", "테스트용 비밀번호", "테스트용 닉네임", "SILVER", "USER");
        return user;
    }

    @Test
    @Rollback(value = true)
    @DisplayName("회원가입 테스트")
    public void userJoinTest() {
        UserVO savedUserVO = userService.save(newUser());
        assertThat(newUser().getUsername()).isEqualTo(savedUserVO.getUsername());
    }

    @Test
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void userloginTest() {
        UserVO savedUserVO = userService.save(newUser());
        // 로그인 객체 생성 후 로그인
        UserVO loginUserVO = new UserVO();
        loginUserVO.setUsername(savedUserVO.getUsername());
        loginUserVO.setPassword(savedUserVO.getPassword());
        UserVO loginResult = userService.login(loginUserVO);
        // 로그인 결과가 not null 이면 테스트 통과
        assertThat(loginResult).isNotNull();
    }

    @Test
    @Rollback(value = true)
    @DisplayName("레벨업 테스트 (BRONZE -> SILVER)")
    public void levelUpTest1() {
        UserVO levelUpUser = newUser();
        levelUpUser.setUserLevel("BRONZE");
        Long savedId = userService.save(levelUpUser).getId();
        UserVO levelUpResult = userService.levelUp(savedId);
        assertThat(levelUpResult.getUserLevel()).isEqualTo("SILVER");
    }

    @Test
    @Rollback(value = true)
    @DisplayName("레벨업 테스트 (SILVER -> GOLD)")
    public void levelUpTest2() {
        UserVO levelUpUser = newUser();
        levelUpUser.setUserLevel("SILVER");
        Long savedId = userService.save(levelUpUser).getId();
        UserVO levelUpResult = userService.levelUp(savedId);
        assertThat(levelUpResult.getUserLevel()).isEqualTo("GOLD");
    }

    @Test
    @Rollback(value = true)
    @DisplayName("레벨업 테스트 (GOLD -> GOLD)")
    public void levelUpTest3() {
        UserVO levelUpUser = newUser();
        levelUpUser.setUserLevel("GOLD");
        Long savedId = userService.save(levelUpUser).getId();
        UserVO levelUpResult = userService.levelUp(savedId);
        assertThat(levelUpResult.getUserLevel()).isEqualTo("GOLD");
    }

}
