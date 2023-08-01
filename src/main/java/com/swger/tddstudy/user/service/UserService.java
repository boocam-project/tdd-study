package com.swger.tddstudy.user.service;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserVO;
import com.swger.tddstudy.user.exception.LoginFailureException;
import com.swger.tddstudy.user.repository.UserRepository;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.request.LoginRequest;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserVO save(JoinRequest joinRequest) {
        UserVO userVO = joinRequest.toUserVO();
        userVO.setUserLevel("BRONZE");
        userVO.setType("USER");
        return userRepository.save(userVO.toEntity()).toUserVO();
    }

    public UserVO saveAdmin(JoinRequest joinRequest) {
        UserVO userVO = joinRequest.toUserVO();
        userVO.setUserLevel("BRONZE");
        userVO.setType("ADMIN");
        return userRepository.save(userVO.toEntity()).toUserVO();
    }

    public UserVO login(LoginRequest loginRequest) {
        Optional<User> optionalUserEntity = userRepository.findByUsername(loginRequest.getUsername());
        if (optionalUserEntity.isPresent()) {
            User loginEntity = optionalUserEntity.get();
            if (loginEntity.getPassword().equals(loginRequest.getPassword())) {
                return loginEntity.toUserVO();
            } else {
                throw new LoginFailureException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new LoginFailureException("일치하는 회원정보가 없습니다.");
        }
    }
}
