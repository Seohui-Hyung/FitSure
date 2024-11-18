package com.pretzero.fitsure.controller;

import com.pretzero.fitsure.model.dto.User;
import com.pretzero.fitsure.model.service.UserService;
import com.pretzero.fitsure.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/token")
public class UserControllerToken {

    @Autowired
    private UserService userService;

    private final String SECRET_KEY = "your-256-bit-secret"; // 암호화 키
    private final long EXPIRATION_TIME_MS = 86400000; // 토큰 만료 시간 (1일)

    @Autowired
    private JwtUtil jwtUtil;
    
    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        int success = userService.signup(user);
        
        if(success > 0) {
        	return ResponseEntity.ok("Signup successful");
        } else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signup failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        // 사용자 인증 (UserService에서 해당 메서드를 구현해야 합니다)
        User authenticatedUser = userService.login(user.getUserLoginId(), user.getPassword());

        if (authenticatedUser != null) {
            // JWT 토큰 생성
            String token = JwtUtil.generateToken(authenticatedUser.getUserId(), authenticatedUser.getUsername(), EXPIRATION_TIME_MS);

            // 응답 데이터 구성
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            // 인증 실패 응답
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid username or password");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    // 로그아웃: 클라이언트 측에서 토큰 제거하도록 안내
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
    	
        return ResponseEntity.ok("Logout successful.");
    }

    // 회원 탈퇴
    @PatchMapping("/delete")
    public ResponseEntity<String> deleteUser(HttpServletRequest request, @RequestBody Map<String, String> credentials) {
        String token = request.getHeader("access-token").substring(7); // "Bearer " 제거
        String loginId = JwtUtil.getLoginId(token);
        
        if (loginId == null || !credentials.get("password").equals(userService.getPassword(loginId))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        boolean deleted = userService.deleteUser(loginId);
        return deleted ? ResponseEntity.ok("Account deleted successfully") : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete account");
    }

    // 토큰을 통한 사용자 인증
//    @GetMapping("/profile")
//    public ResponseEntity<User> getProfile(HttpServletRequest request) {
//        String token = request.getHeader("access-token").substring(7);
//        
//        if (JwtUtil.isExpired(token, SECRET_KEY)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//
//        String loginId = JwtUtil.getLoginId(token, SECRET_KEY);
//        User user = userService.findUserByLoginId(loginId);
//        
//        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//    }
    
    @PostMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestBody User user) {

        String userId = userService.findUserId(user);
        return userId != null ? ResponseEntity.ok(userId) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to find account");
    }
    
    
    
}
