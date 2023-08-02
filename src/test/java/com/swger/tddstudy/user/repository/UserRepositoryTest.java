package com.swger.tddstudy.user.repository;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.util.IntegrationTestSupport;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest extends IntegrationTestSupport {

    @Autowired
    UserRepository userRepository;

    @DisplayName("findByUsername()은")
    @Nested
    class Context_findByUsername {

        @DisplayName("username으로 유저를 검색할 수 있다.")
        @Test
        void _willSuccess() {
            // given
            User user1 = User.createNewUser("testUser1", "password", "테스트유저1");
            User savedUser = userRepository.save(user1);

            // when
            User result = userRepository.findByUsername(user1.getUsername()).get();

            // then
            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).extracting(
                "username", "password", "nickname", "userLevel", "type"
            ).containsExactly(user1.getUsername(), user1.getPassword(), user1.getNickname(),
                user1.getUserLevel(), user1.getType());
        }

        @DisplayName("부정확한 username의 경우 찾을 수 없다.")
        @Test
        void incorrectUsername_willFail() {
            // given
            User user1 = User.createNewUser("testUser1", "password", "테스트유저1");
            String wrongUsername = "testUser2";
            User savedUser = userRepository.save(user1);

            Optional<User> result = userRepository.findByUsername(wrongUsername);

            // when then
            Assertions.assertThatThrownBy(result::get)
                .isInstanceOf(NoSuchElementException.class);
        }
    }
}
