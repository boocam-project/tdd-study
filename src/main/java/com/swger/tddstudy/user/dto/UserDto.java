package com.swger.tddstudy.user.dto;
import java.util.Optional;

import lombok.*;
import com.swger.tddstudy.user.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    // 회원가입 요청 DTO


    private String username; // 사용자 아이디

    private String password; // 비밀번호

    private String nickname; // 닉네임


    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        return user;
    }

    public static UserDto convertToDto(Optional<User> user) {
        if(user.isPresent()){
            User u = user.get();
            UserDto userDto = new UserDto();
            userDto.setNickname(u.getNickname());
            return userDto;
        }

        return null;
    }


}