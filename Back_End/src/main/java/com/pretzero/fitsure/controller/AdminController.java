package com.pretzero.fitsure.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pretzero.fitsure.model.dto.Admin;
import com.pretzero.fitsure.model.dto.Comment;
import com.pretzero.fitsure.model.dto.InsurancePlan;
import com.pretzero.fitsure.model.dto.User;
import com.pretzero.fitsure.model.service.AdminService;
import com.pretzero.fitsure.model.service.CommentService;
import com.pretzero.fitsure.model.service.InsurancePlanService;
import com.pretzero.fitsure.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private InsurancePlanService insurancePlanService;

	@Autowired
	private CommentService commentService;

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Admin admin, HttpSession session) {
		Admin loginAdmin = adminService.login(admin.getAdminName(), admin.getAdminPassword());
		if (loginAdmin != null) {
			session.setAttribute("admin", loginAdmin);
			return new ResponseEntity<>("Login successful", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
	}
	
	
	@GetMapping("/users")
	public ResponseEntity<?> getUserList(){
		List<User> userList = userService.getUserList();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	

	// 보험 등록
	@PostMapping("/insurance/add")
	public ResponseEntity<String> insuranceadd(@RequestBody InsurancePlan insurancePlan,
			@RequestParam MultipartFile file) {
		try {

			// 상세 페이지에서 보일 창은 이미지 대체하기 위한
			String insuranceDetail = saveFile(file);
			insurancePlan.setDetail(insuranceDetail);

			insurancePlanService.addinsurance(insurancePlan);
			return new ResponseEntity<>("Insurance registration was successful", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("등록에 실패했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// 보험 삭제
	@DeleteMapping("/insurance/delete/{insuranceId}")
	public ResponseEntity<String> disableInsurance(@PathVariable int insuranceId) {
		if (insurancePlanService.disableInsurance(insuranceId))
			return ResponseEntity.status(HttpStatus.OK).body("insurance operation successfully terminated");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request application failed.");
	}

	// 보험 상세 보기
	@GetMapping("/insurance/{insuranceId}")
	public ResponseEntity<?> detail(@PathVariable int insuranceId) {
		InsurancePlan insurancePlan = insurancePlanService.readInsurance(insuranceId);
		List<Comment> comments = commentService.getCommentList(insuranceId);

		if (insurancePlan != null) {
			Map<String, Object> response = new HashMap<>();

			String filePath = "/img/" + insurancePlan.getDetail();
			insurancePlan.setFileUrl(filePath);

			response.put("insurancePlan", insurancePlan);
			response.put("comments", comments);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	// 보험 수정 (JSON 형태로 보낸다)
	@PutMapping("/insurance")
	public ResponseEntity<String> modifyInsurance(@RequestBody InsurancePlan insurancePlan) {

		if (insurancePlanService.modifyInsurance(insurancePlan)) {
			return ResponseEntity.ok("Insurance updated successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update insurance");
	}

	// 보험 댓글 삭제
	@DeleteMapping("/insurance/{insuranceId}/comments/{commentId}")
	public ResponseEntity<String> adminDeleteComment(@PathVariable int insuranceId, @PathVariable int commentId) {
		if (commentService.deleteComment(insuranceId, commentId)) {
			return ResponseEntity.ok("Comment deleted successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Comment not found or does not belong to the insurance");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 파일 저장 메서드
	private String saveFile(MultipartFile file) throws IOException {
		String filePath = "src/main/resources/static/img/" + file.getOriginalFilename(); // 예: 서버 경로에 파일 저장
		File destination = new File(filePath);
		file.transferTo(destination);
		return filePath; // 저장된 파일 경로 반환
	}

}
