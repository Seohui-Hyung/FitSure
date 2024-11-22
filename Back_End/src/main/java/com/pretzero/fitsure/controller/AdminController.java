package com.pretzero.fitsure.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.pretzero.fitsure.model.dto.Admin;
import com.pretzero.fitsure.model.dto.Comment;
import com.pretzero.fitsure.model.dto.InsurancePlan;
import com.pretzero.fitsure.model.dto.SearchCondition;
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
	public ResponseEntity<?> login(@RequestBody Admin admin, HttpSession session) {
		Admin loginAdmin = adminService.login(admin.getAdminName(), admin.getAdminPassword());
		if (loginAdmin != null) {
			session.setAttribute("admin", loginAdmin);
			Map<String, Integer> response = new HashMap<>();
			response.put("name", loginAdmin.getAdminId());

			return ResponseEntity.ok(response);
		} else {
			return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		System.out.println("testdone");
		session.invalidate();
		return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<?> getUserList() {
		List<User> userList = userService.getUserList();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@GetMapping("/users/search")
	public ResponseEntity<?> search(@ModelAttribute SearchCondition condition, Model model) {

		if (condition.getKey().equals("gender")) {
			if (condition.getWord().equals("남성")) {
				condition.setWord("M");
			} else {
				condition.setWord("Y");
			}
		} else if (condition.getKey().equals("available")) {
			if (condition.getWord().equals("X")) {
				condition.setWord("0");
			} else {
				condition.setWord("1");
			}
		}

		List<User> userList = userService.search(condition);
		System.out.println();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	// 보험 등록
	@PostMapping("/insurance/add")
	public ResponseEntity<String> insuranceadd(
			@RequestPart("insurancePlan") InsurancePlan insurancePlan,
	        @RequestParam(value = "file", required = false) MultipartFile file) {
	    try {
	        String insuranceDetail = saveFile(file);
	        insurancePlan.setDetail(insuranceDetail);
	        System.out.println(insurancePlan.getInsuranceName());
	        
	        insurancePlanService.addinsurance(insurancePlan);
	        return new ResponseEntity<>("Insurance registration was successful", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("등록에 실패했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	// 보험 삭제
	@DeleteMapping("/insurance/delete/{insuranceId}")
	public ResponseEntity<String> disableInsurance(@PathVariable int insuranceId) {
		if (insurancePlanService.disableInsurance(insuranceId))
			return ResponseEntity.status(HttpStatus.OK).body("insurance operation successfully terminated");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request application failed.");
	}

	// 보험 목록 보기
	@GetMapping("/insurance")
	public ResponseEntity<?> insuranceList() {
		List<InsurancePlan> list = insurancePlanService.getInsuranceList();
		return new ResponseEntity<>(list, HttpStatus.OK);

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

	@PutMapping("/insurance/{insuranceId}")
	public ResponseEntity<String> modifyInsurance(
	    @PathVariable("insuranceId") int insuranceId,
	    @RequestPart("insurancePlan") InsurancePlan insurancePlan,
	    @RequestPart(value = "file", required = false) MultipartFile file) {

	    try {
	        // 파일 저장 처리
	        if (file != null) {
	            String insuranceDetail = saveFile(file);
	            insurancePlan.setDetail(insuranceDetail);
	        }

	        // 보험 수정 처리
	        insurancePlanService.modifyInsurance(insurancePlan);
	        return new ResponseEntity<>("Insurance modification was successful", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("수정에 실패했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
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

	private String saveFile(MultipartFile file) throws IOException {
	    String uploadDir = "C:/upload/img"; // 외부 디렉터리
	    File dir = new File(uploadDir);
	    if (!dir.exists()) {
	        dir.mkdirs(); // 디렉터리가 없으면 생성
	    }

	    String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	    File destination = new File(dir, uniqueFileName);
	    file.transferTo(destination);

	    return destination.getAbsolutePath(); // 저장된 파일의 절대 경로 반환
	}

}
