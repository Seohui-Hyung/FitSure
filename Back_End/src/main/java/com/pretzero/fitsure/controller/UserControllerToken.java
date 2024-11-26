package com.pretzero.fitsure.controller;

import com.pretzero.fitsure.model.dto.User;
import com.pretzero.fitsure.model.service.MailService;
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

    private final long EXPIRATION_TIME_MS = 86400000; // 토큰 만료 시간 (1일)

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private MailService mailService;
    
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
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user, HttpServletRequest request) {
        // 사용자 인증 (UserService에서 해당 메서드를 구현해야 합니다)
        User authenticatedUser = userService.login(user.getUserLoginId(), user.getPassword());

        if (authenticatedUser != null) {
            // JWT 토큰 생성
            String token = JwtUtil.generateToken(authenticatedUser.getUserId(), authenticatedUser.getUsername(), EXPIRATION_TIME_MS);

            // 응답 데이터 구성
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("access-token", token);
            response.put("JSESSIONID", request.getRequestedSessionId());

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

    
    // 마이 페이지 확인
    @GetMapping("/myPage")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
    	 String token = request.getHeader("access-token");
         String userLoginId = JwtUtil.getLoginId(token);
        
        if (userLoginId == null) {
        	Map<String, String> response = new HashMap<>();
        	response.put("message", "This service requires login. Please log in.");
        	response.put("redirectUrl", "/login"); // 클라이언트가 사용할 URL

        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        }

        User user = userService.userInfo(userLoginId);
        
        return ResponseEntity.ok(user);
    }
    
    
    
    
    @PostMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestBody User user) {

        String userId = userService.findUserId(user);
        return userId != null ? ResponseEntity.ok(userId) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to find account");
    }
    
    
    // 비밀번호 분실로 인한 변경  
    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestBody User user) {
        
        int result = mailService.sendMailForPassword(user.getUserLoginId(), user.getEmail());


        return result > 0 ? ResponseEntity.ok("Password updated successfully") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password");
    }
    
    
    
    // 비밀번호 확인 
    @PostMapping("/verifyPassword")
    public ResponseEntity<String> verifyPassword(HttpServletRequest request, @RequestBody User user) {
        String token = request.getHeader("access-token"); 
        String userLoginId = JwtUtil.getLoginId(token);
        
        // user 객체의 비밀번호를 비교
        if (userLoginId == null || !userService.getPassword(userLoginId).equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        return ResponseEntity.ok("Password verified");
    }


    // 회원 탈퇴
    @PatchMapping("/delete")
    public ResponseEntity<String> deleteUser(HttpServletRequest request, @RequestBody User user) {
        String token = request.getHeader("access-token"); 
        String userLoginId = JwtUtil.getLoginId(token);
        
        if (userLoginId == null || !userService.getPassword(userLoginId).equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        boolean deleted = userService.deleteUser(userLoginId);
        
        return deleted ? ResponseEntity.ok("Account deleted successfully") : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete account");
    }
    
    
    @PatchMapping("/update")
    public ResponseEntity<String> updateUserInfo(HttpServletRequest request, @RequestBody User updatedUser) {
        String token = request.getHeader("access-token"); 
        String userLoginId = JwtUtil.getLoginId(token);

        if (userLoginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This service requires login.");
        }
        
        User existingUser = userService.userInfo(userLoginId);

        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setBirthDate(updatedUser.getBirthDate());

        boolean updated = userService.updateUserInfo(existingUser);

        return updated ? ResponseEntity.ok("User information updated successfully") 
                       : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user information");
    }

    // 인증된 사용자 정보 반환
    @GetMapping("/auth/me")
    public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request) {
    	String token = request.getHeader("access-token"); 
        String userLoginId = JwtUtil.getLoginId(token);

        if (userLoginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This service requires login.");
        }
        
        User existingUser = userService.userInfo(userLoginId);
		return existingUser != null ? ResponseEntity.ok(existingUser) 
                       : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user information");
    }
    
    
}
