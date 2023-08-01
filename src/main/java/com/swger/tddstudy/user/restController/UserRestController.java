package com.swger.tddstudy.user.restController;

import com.swger.tddstudy.user.domain.UserVO;
import com.swger.tddstudy.user.service.UserRegex;
import com.swger.tddstudy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserRestController {

    private final UserService userService;
    private final UserRegex regex;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserVO userVO) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> test = regex.isJoinRegex(userVO);
        if ((Boolean) test.get("result")) {
            UserVO savedUser = userService.save(userVO);
            message.put("status", 200);
            message.put("data", savedUser);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            message.put("status", 400);
            message.put("message", test.get("message"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @PostMapping("/join/admin")
    public ResponseEntity<?> joinAdmin(@RequestBody UserVO userVO) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> test = regex.isJoinRegex(userVO);
        if ((Boolean) test.get("result")) {
            UserVO savedUser = userService.saveAdmin(userVO);
            message.put("status", 200);
            message.put("data", savedUser);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            message.put("status", 400);
            message.put("message", test.get("message"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserVO userVO, HttpSession session) {
        Map<String, Object> message = new HashMap<>();
        UserVO loginResult = userService.login(userVO);
        if (loginResult != null) {
            session.setAttribute("username", loginResult.getUsername());
            session.setAttribute("id", loginResult.getId());
            message.put("status", 200);
            message.put("data", loginResult);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            message.put("status", 401);
            message.put("message", "로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
    }

}
