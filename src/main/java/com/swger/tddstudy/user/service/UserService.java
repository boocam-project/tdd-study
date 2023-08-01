package com.swger.tddstudy.user.service;

import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserVO;
import com.swger.tddstudy.user.repository.UserRepository;
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

    public UserVO save(UserVO userVO) {
        userVO.setUserLevel("BRONZE");
        userVO.setType("USER");
        return userRepository.save(userVO.toEntity()).toUserVO();
    }

    public UserVO saveAdmin(UserVO userVO) {
        userVO.setUserLevel("BRONZE");
        userVO.setType("ADMIN");
        return userRepository.save(userVO.toEntity()).toUserVO();
    }

    public UserVO login(UserVO userVO) {
        /*
        login.html에서 입력받은 이름, 비밀번호를 받아오고
        DB에서 해당 이름을 가져와서
        입력받은 비밀번호롸 DB에서 조회한 비밀번호의 일치여부를 판단하여
        일치하면 로그인 성공, 일치하지 않으면 로그인 실패
         */
        Optional<User> optionalUserEntity = userRepository.findByUsername(userVO.getUsername());
        if (optionalUserEntity.isPresent()) {
            User loginEntity = optionalUserEntity.get();
            if (loginEntity.getPassword().equals(userVO.getPassword())) {
                return loginEntity.toUserVO();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
