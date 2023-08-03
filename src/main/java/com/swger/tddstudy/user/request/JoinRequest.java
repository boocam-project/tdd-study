package com.swger.tddstudy.user.request;

import com.swger.tddstudy.user.domain.UserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JoinRequest {

    @NotBlank(message = "이름을 입력하세요.")
    @Size(min = 2, max = 10, message = "이름을 2자 이상 10자 이하로 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, max = 15, message = "비밀번호를 8자 이상 10자 이하로 입력하세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 2, max = 10, message = "닉네임을 2자 이상 10자 이하로 입력하세요. ")
    private String nickname;

    public UserDto toUserDto() {
        return UserDto.builder().username(this.username).password(this.password).nickname(this.nickname).build();
    }
}
