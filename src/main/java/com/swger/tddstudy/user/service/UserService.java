package com.swger.tddstudy.user.service;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserDto;
import com.swger.tddstudy.user.exception.LoginFailureException;
import com.swger.tddstudy.user.repository.UserRepository;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserDto save(JoinRequest joinRequest) {
        UserDto userDto = joinRequest.toUserDto();
        userDto.setUserLevel("BRONZE");
        userDto.setType("USER");
        return userRepository.save(userDto.toEntity()).toUserDto();
    }

    public UserDto saveAdmin(JoinRequest joinRequest) {
        UserDto userDto = joinRequest.toUserDto();
        userDto.setUserLevel("BRONZE");
        userDto.setType("ADMIN");
        return userRepository.save(userDto.toEntity()).toUserDto();
    }

    public UserDto login(LoginRequest loginRequest) {
        Optional<User> optionalUserEntity = userRepository.findByUsername(loginRequest.getUsername());
        if (optionalUserEntity.isPresent()) {
            User loginEntity = optionalUserEntity.get();
            if (loginEntity.getPassword().equals(loginRequest.getPassword())) {
                return loginEntity.toUserDto();
            } else {
                throw new LoginFailureException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new LoginFailureException("일치하는 회원정보가 없습니다.");
        }
    }
}
