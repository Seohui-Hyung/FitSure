package com.pretzero.fitsure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretzero.fitsure.model.dto.Comment;
import com.pretzero.fitsure.model.dto.InsurancePlan;
import com.pretzero.fitsure.model.service.CommentService;
import com.pretzero.fitsure.model.service.InsurancePlanService;
import com.pretzero.fitsure.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/insurance") // 사용자를 위한 insuranceController 
public class InsuranceController {
	
	@Autowired
	InsurancePlanService insuranceService;
	
	@Autowired
	CommentService commentService;
	
	// 보험 전체 목록 조회 
	@GetMapping("")
	public ResponseEntity<?> insuranceList(){
		List<InsurancePlan> list = insuranceService.getInsuranceList();
		if (list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<InsurancePlan>>(list, HttpStatus.OK);
	}
	
	// 보험 상세 내역 조회 
	@GetMapping("/{insuranceId}")
	public ResponseEntity<Map<String, Object>> detailInsurance(@PathVariable int insuranceId) {
	    InsurancePlan insurancePlan = insuranceService.readInsurance(insuranceId);
	    List<Comment> comments = commentService.getCommentList(insuranceId);

	    if (insurancePlan != null) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("insurancePlan", insurancePlan);
	        response.put("comments", comments);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	
	// 보험 결제의 경우 PayController로 별도 진행 
	
	// 보험 댓글 작성 
	@PostMapping("/{insuranceId}")
	public ResponseEntity<?> commentInsurance(@PathVariable int insuranceId, @RequestBody Comment comment, HttpServletRequest request){
		String token = request.getHeader("access-token").substring(7); 
		int userId = JwtUtil.getuserId(token);
		
		if (userId < 0 || JwtUtil.isExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
		
		comment.setUserId(userId);
		comment.setInsuranceId(insuranceId);
		
		if (commentService.writeComment(comment)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Comment added successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add comment");
	}
	
	
	
	
	
	
	
	
	
}
