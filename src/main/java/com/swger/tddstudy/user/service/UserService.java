package com.swger.tddstudy.user.service;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.repository.UserRepository;
import com.swger.tddstudy.user.service.dto.request.SignUpServiceRequest;
import com.swger.tddstudy.user.service.dto.request.LoginServiceRequest;
import com.swger.tddstudy.user.service.dto.response.LoginResponse;
import com.swger.tddstudy.user.service.dto.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public SignUpResponse signUp(SignUpServiceRequest request) {

        if (!validateRePassword(request.getPassword(), request.getRePassword())) {
            throw new IllegalArgumentException("Re-Password Not Match");
        }

        User newUser = User.createNewUser(request.getUsername(), request.getPassword(),
            request.getNickname());

        return SignUpResponse.toDto(userRepository.save(newUser));
    }

    private boolean validateRePassword(String password, String rePassword) {
        return password.equals(rePassword);
    }

    public LoginResponse login(LoginServiceRequest request) {
        User findUser = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("적절하지 않은 로그인 정보입니다."));

        if (!isPasswordCorrect(request.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("적절하지 않은 로그인 정보입니다.");
        }

        return LoginResponse.toDto(findUser);
    }

    private boolean isPasswordCorrect(String ownPassword, String password) {
        return ownPassword.equals(password);
    }
}
