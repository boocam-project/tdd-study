package com.swger.tddstudy.user.controller;

import com.swger.tddstudy.user.domain.UserDto;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.request.LoginRequest;
import com.swger.tddstudy.user.service.UserService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("join-form")
    public String joinForm() {
        return "userPages/join";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "userPages/login";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute JoinRequest joinRequest) {
        userService.save(joinRequest);
        return "userPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, HttpSession session) {
        UserDto loginResult = userService.login(loginRequest);
        if (loginResult != null) {
            session.setAttribute("username", loginResult.getUsername());
            session.setAttribute("id", loginResult.getId());
            return "main";
        } else {
            return "userPages/login";
        }
    }
}
