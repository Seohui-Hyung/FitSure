package com.pretzero.fitsure.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pretzero.fitsure.model.dto.GoalResult;
import com.pretzero.fitsure.model.service.GoalResultService;
import com.pretzero.fitsure.model.service.GoalService;
import com.pretzero.fitsure.model.service.UserService;
import com.pretzero.fitsure.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/goal")
public class GoalController {
	
	@Autowired
	GoalService goalService;
	
	@Autowired
	GoalResultService goalResultService;
	
	@Autowired
	UserService userService;
	
	private final String SECRET_KEY = "your-256-bit-secret"; // 암호화 키

    @Autowired
    private JwtUtil jwtUtil;
	
	@PostMapping("/create")
    public ResponseEntity<String> createGoal(HttpServletRequest request, @RequestBody Map<String, String> goalDetails) {
        String token = request.getHeader("Authorization").substring(7); // "Bearer " 제거
        int userId = JwtUtil.getuserId(token);

        // 토큰의 유효성을 검사하고 만료된 경우 Unauthorized 응답
        if (userId < 0 || JwtUtil.isExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        int goalCreated = goalService.createGoal(userId);

        if (goalCreated > 0) {
            return ResponseEntity.ok("Goal created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Goal already exists or could not be created");
        }
    }
	
	
	// 목표 달성
	@PostMapping("/done")
	public ResponseEntity<String> completeGoal(HttpServletRequest request,
	                                           @RequestParam("file") MultipartFile file,
	                                           @RequestParam("walking") int walking) throws IOException { // 추가된 걸음 수 파라미터
	    String token = request.getHeader("Authorization").substring(7);
	    int userId = JwtUtil.getuserId(token);

	    if (userId < 0 || JwtUtil.isExpired(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
	    }

	    int goalId = goalService.findGoal(userId);
	    
	    if (goalId == 0) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal not found for this user");
	    }

	    if (file.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or empty file uploaded");
	    }
	    
	    String attachmentPath = saveFile(file);  // saveFileToServer는 파일 저장 메서드

	    // GoalResult 생성 및 필드 설정
		GoalResult goalResult = new GoalResult();
		goalResult.setGoalId(goalId);
		goalResult.setUserId(userId);
		goalResult.setWalking(walking);
        goalResult.setAttachment(attachmentPath);  

		int goalCompleted = goalResultService.completeGoal(goalResult);

		if (goalCompleted == 1) {
		    return ResponseEntity.ok("successfully");
		} else {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Goal completion failed or goal not found");
		}
	}

	
	
	
	
	
	
	
	
	
	
	// 7일이 지난 목표 중 dayGoal이 5 이상인 목표의 weekGoal 증가
    @Scheduled(cron = "0 0 0 * * *")
    public void updateWeeklyGoals() {
    	goalService.incrementWeekGoal();
    }
    
    
    // 파일 저장 메서드
    private String saveFile(MultipartFile file) throws IOException {
        String filePath = "src/main/resources/static/img/" + file.getOriginalFilename();  // 예: 서버 경로에 파일 저장
        File destination = new File(filePath);
        file.transferTo(destination);
        return filePath;  // 저장된 파일 경로 반환
    }
    
	
}
