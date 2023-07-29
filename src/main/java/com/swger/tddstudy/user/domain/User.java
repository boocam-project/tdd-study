package com.swger.tddstudy.user.domain;

import com.swger.tddstudy.util.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserLevel userLevel;

    @Enumerated(EnumType.STRING)
    private UserType type;

    private User(String username, String password, String nickname, UserLevel userLevel,
        UserType type) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userLevel = userLevel;
        this.type = type;
    }

    public static User createNewUser(String username, String password, String nickname) {
        return new User(username, password, nickname, UserLevel.BRONZE, UserType.USER);
    }

    public static User createNewAdmin(String username, String password, String nickname) {
        return new User(username, password, nickname, UserLevel.BRONZE, UserType.ADMIN);
    }

    public void upgradeLevel() {
        this.userLevel = this.userLevel.levelUp();
    }
}
