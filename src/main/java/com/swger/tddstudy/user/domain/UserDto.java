package com.swger.tddstudy.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String userLevel;
    private String type;

    public UserDto(String username, String password, String nickname, String userLevel,
        String type) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userLevel = userLevel;
        this.type = type;
    }

    public User toEntity() {
        return User.builder().username(this.username)
            .password(this.password).nickname(this.nickname)
            .userLevel(UserLevel.valueOf(this.userLevel)).type(UserType.valueOf(this.type)).build();
    }
}
