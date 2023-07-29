package com.swger.tddstudy.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("일반 유저 계정을 생성할 수 있다.")
    @Test
    void createNewUser_WillSuccess() {
        // when
        User newUser = User.createNewUser("user1", "1234", "닉네임1");

        // then
        Assertions.assertThat(newUser)
            .extracting("username", "password", "nickname", "userLevel", "type")
            .containsExactly("user1", "1234", "닉네임1", UserLevel.BRONZE, UserType.USER);
    }

    @DisplayName("관리자 계정을 생성할 수 있다.")
    @Test
    void createNewAdmin_WillSuccess() {
        // when
        User newUser = User.createNewAdmin("user1", "1234", "닉네임1");

        // then
        Assertions.assertThat(newUser)
            .extracting("username", "password", "nickname", "userLevel", "type")
            .containsExactly("user1", "1234", "닉네임1", UserLevel.BRONZE, UserType.ADMIN);
    }

    @DisplayName("upgradeLevel()는 ")
    @Nested
    class Context_upgradeLevel {

        @DisplayName("등급을 업그레이드 할 수 있다.")
        @Test
        void upgradeLevel_willSuccess(){
            // given
            User newUser = User.createNewUser("user1", "1234", "닉네임1");

            // when
            newUser.upgradeLevel();

            // then
            Assertions.assertThat(newUser.getUserLevel()).isSameAs(UserLevel.SILVER);
        }

        @DisplayName("최고 등급의 경우 업그레이드 할 수 없다.")
        @Test
        void upgradeLevel_maxLevel_willFail(){
            // given
            User newUser = User.createNewUser("user1", "1234", "닉네임1");
            newUser.upgradeLevel();
            newUser.upgradeLevel();
            newUser.upgradeLevel();
            newUser.upgradeLevel();
            Assertions.assertThat(newUser.getUserLevel()).isSameAs(UserLevel.GOLD);

            // when
            newUser.upgradeLevel();

            // then
            Assertions.assertThat(newUser.getUserLevel()).isSameAs(UserLevel.GOLD);
        }

    }

}
