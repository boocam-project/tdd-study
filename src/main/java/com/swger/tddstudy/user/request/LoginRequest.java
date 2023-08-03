package com.swger.tddstudy.user.request;

import com.swger.tddstudy.user.domain.UserDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "이름을 입력하세요.")
    @Size(min = 2, max = 10, message = "이름을 2자 이상 10자 이하로 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, max = 15, message = "비밀번호를 8자 이상 10자 이하로 입력하세요.")
    private String password;

    public UserDto toUserDto() {
        return UserDto.builder().username(this.username).password(this.password).build();
    }
}
