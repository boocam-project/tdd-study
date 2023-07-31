package com.swger.tddstudy.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import com.swger.tddstudy.user.dto.UserDto;
import com.swger.tddstudy.user.entity.User;
import com.swger.tddstudy.user.repository.UserRepository;
import com.swger.tddstudy.user.request.LoginRequest;
import com.swger.tddstudy.user.response.LogoutResponse;
import com.swger.tddstudy.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    @PostMapping("/users/new-user") // 회원가입
    public ResponseEntity<String> join(@RequestBody UserDto userDto) { // @Valid 어노테이션 추가
        try {
            userService.save(userDto);
            return ResponseEntity.ok("Join success"); // 메시지 명시
        } catch (DuplicateKeyException e) { // 중복된 이메일인 경우
            return ResponseEntity.badRequest().body("Join failed: Email already exists");
        } catch (Exception e) { // 그 외의 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패: " + e.getMessage());
        }
    }

    @PostMapping("/users/login") // 로그인
    public Map<String, Object> login(@RequestBody LoginRequest request, HttpSession httpSession, HttpServletResponse response) {
        User user = userService.findByUsername(request.getUsername());
        Map<String, Object> responseBody = new HashMap<>();

        if (user == null) {
            responseBody.put("status", "error");
            responseBody.put("message", "User not found");
            return responseBody;
        }

        if (!request.getPassword().equals(user.getPassword())) {
            responseBody.put("status", "error");
            responseBody.put("message", "Invalid password");
            return responseBody;
        }


        userService.save(user); // 변경된 로그인 상태 저장

        httpSession.setAttribute("user", user); // 세션에 로그인 정보 유지

        // 세션 아이디를 쿠키에 추가
        Cookie sessionCookie = new Cookie("sessionId", httpSession.getId());
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(-1); // 브라우저를 닫으면 쿠키가 삭제됩니다.
        response.addCookie(sessionCookie);

        responseBody.put("sessionId", httpSession.getId());
        responseBody.put("nickname", user.getNickname());
        responseBody.put("email", user.getUsername());
        responseBody.put("password", user.getPassword());

        responseBody.put("message", "Login Success");

        return responseBody;
    }

    @GetMapping("/checkDuplicate/{nickname}") // 닉네임 중복 확인
    public boolean checkDuplicateNickname(@PathVariable String nickname) {
        return userService.checkDuplicateNickname(nickname);
    } // 중복이면 true, 아니면 false


    @DeleteMapping("/delete") // 회원탈퇴
    public ResponseEntity<String> deleteUser(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        try {
            userService.deleteUser(user.getId());
            httpSession.invalidate(); // 세션 무효화
            return ResponseEntity.ok("탈퇴가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
        }
    }

    @PostMapping("/user/logout") // 로그아웃
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            session.removeAttribute("user"); // 세션에서 로그인 정보 삭제
            session.invalidate(); // 세션 무효화
            if (user != null) {

                userRepository.save(user); // 업데이트된 User 엔티티를 데이터베이스에 저장
            }

            // 세션 쿠키 삭제
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
            LogoutResponse logoutResponse = new LogoutResponse("로그아웃 되었습니다.");
            return ResponseEntity.ok(logoutResponse);
        } else {
            LogoutResponse logoutResponse = new LogoutResponse("로그인 상태가 아닙니다.");
            return ResponseEntity.badRequest().body(logoutResponse);
        }
    }

}
//인텔리제이 깃연동