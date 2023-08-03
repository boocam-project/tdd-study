package com.swger.tddstudy.user.restController;

import com.swger.tddstudy.user.domain.UserDto;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.request.LoginRequest;
import com.swger.tddstudy.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequest joinRequest) {
        Map<String, Object> message = new HashMap<>();
        message.put("status", 200);
        message.put("data", userService.save(joinRequest));
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/join/admin")
    public ResponseEntity<?> joinAdmin(@Valid @RequestBody JoinRequest joinRequest) {
        Map<String, Object> message = new HashMap<>();
        message.put("status", 200);
        message.put("data", userService.saveAdmin(joinRequest));
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,
        HttpSession session) {
        Map<String, Object> message = new HashMap<>();
        UserDto loginResult = userService.login(loginRequest);
        session.setAttribute("username", loginResult.getUsername());
        session.setAttribute("id", loginResult.getId());
        message.put("status", 200);
        message.put("data", loginResult);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
