package com.swger.tddstudy.user.controller;

import com.swger.tddstudy.user.controller.dto.request.LoginRequest;
import com.swger.tddstudy.user.controller.dto.request.SignUpRequest;
import com.swger.tddstudy.user.service.UserService;
import com.swger.tddstudy.user.service.dto.response.LoginResponse;
import com.swger.tddstudy.user.service.dto.response.SignUpResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/signUp")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.signUp(requestDto.toServiceDto()));
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest requestDto) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.login(requestDto.toServiceDto()));
    }
}
