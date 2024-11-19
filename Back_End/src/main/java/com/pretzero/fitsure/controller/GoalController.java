package com.pretzero.fitsure.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pretzero.fitsure.model.dto.Goal;
import com.pretzero.fitsure.model.dto.GoalResult;
import com.pretzero.fitsure.model.service.CouponService;
import com.pretzero.fitsure.model.service.GoalResultService;
import com.pretzero.fitsure.model.service.GoalService;
import com.pretzero.fitsure.model.service.UserService;
import com.pretzero.fitsure.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/goal")
public class GoalController {

	@Autowired
	GoalService goalService;

	@Autowired
	GoalResultService goalResultService;

	@Autowired
	UserService userService;

	@Autowired
	CouponService couponService;


	// 목표 생성
	@PostMapping("/create")
	public ResponseEntity<String> createGoal(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); // "Bearer " 제거
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
	public ResponseEntity<String> completeGoal(HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@RequestParam("walking") int walking) throws IOException { // 추가된 걸음 수 파라미터
		String token = request.getHeader("Authorization");
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

		String attachmentPath = saveFile(file); // saveFileToServer는 파일 저장 메서드

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

	// 24주 달성 시, 쿠폰 발급
	@PostMapping("/change/coupon")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> changeCoupon(HttpServletRequest request,
			@RequestBody Map<String, String> goalDetails) {
		String token = request.getHeader("Authorization"); // "Bearer " 제거
		int userId = JwtUtil.getuserId(token);

		if (userId < 0 || JwtUtil.isExpired(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
		}

		boolean available = goalService.achievedWeekGoal(userId);

		try {
			if (available) {
				boolean check1 = couponService.awardCoupon(userId);
				boolean check2 = goalService.changeCoupon(userId);

				// cha1과 cha2가 모두 true인 경우에만 성공 처리
				if (check1 && check2) {
					return ResponseEntity.ok("Receive a coupon upon achieving the goal.");
				} else {
					// cha1이나 cha2가 false이면 롤백 처리
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 롤백 강제
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("An error occurred: Coupon or goal update failed.");
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("You cannot receive a coupon because the goal has not been completed.");
			}
		} catch (Exception e) {
			// 예외 발생 시 롤백 자동 처리
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	@PatchMapping("/cancel")
	public ResponseEntity<String> cancelGoal(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		int userId = JwtUtil.getuserId(token);

		if (userId < 0 || JwtUtil.isExpired(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
		}

		boolean goalCancel = goalService.cancelGoal(userId);

		if (goalCancel) {
			return ResponseEntity.ok("Goal cancel successfully");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Failed to cancel the objective. Please try again.");
		}
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> goalList(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		int userId = JwtUtil.getuserId(token);

		if (userId < 0 || JwtUtil.isExpired(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
		}
		
		Goal ongoingGoal = goalService.getGoalList(userId);
		
		
		if (ongoingGoal != null) {
			return new ResponseEntity<>(ongoingGoal, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
		            .body("No registered goals found. Please create a new goal.");
		}
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	// 7일이 지난 목표 중 dayGoal이 5 이상인 목표의 weekGoal 증가
	@Scheduled(cron = "0 0 0 * * *")
	public void updateWeeklyGoals() {
		goalService.incrementWeekGoal();
	}

	// 파일 저장 메서드
	private String saveFile(MultipartFile file) throws IOException {
		String filePath = "src/main/resources/static/img/" + file.getOriginalFilename(); // 예: 서버 경로에 파일 저장
		File destination = new File(filePath);
		file.transferTo(destination);
		return filePath; // 저장된 파일 경로 반환
	}

}
