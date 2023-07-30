package com.swger.tddstudy.user.restController;

import com.swger.tddstudy.user.domain.UserVO;
import com.swger.tddstudy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserVO> join(@RequestBody UserVO userVO) {
        UserVO savedUser = userService.save(userVO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserVO> login(@RequestBody UserVO userVO, HttpSession session) {
        UserVO loginResult = userService.login(userVO);
        if (loginResult != null) {
            session.setAttribute("username", loginResult.getUsername());
            session.setAttribute("id", loginResult.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(loginResult);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(userVO);
        }
    }

}
