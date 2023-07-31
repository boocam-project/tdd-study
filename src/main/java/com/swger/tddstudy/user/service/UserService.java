package com.swger.tddstudy.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import com.swger.tddstudy.user.dto.UserDto;
import com.swger.tddstudy.user.entity.User;
import com.swger.tddstudy.user.exception.UserNotFoundException;
import com.swger.tddstudy.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void save(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword()); // 인코딩된 패스워드 저장
        user.setNickname(userDto.getNickname());


        userRepository.save(user);
    }


    public void save(User user) {
        userRepository.save(user);
    }


    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException("유저를 찾을 수 없습니다. " + username);
    }



    public boolean checkDuplicateNickname(String username) {
        return userRepository.findByNickname(username).isPresent();
    }


    public void deleteUser(long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        userRepository.delete(user.get());
    }


}