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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pretzero.fitsure.model.dto.Comment;
import com.pretzero.fitsure.model.dto.Coupon;
import com.pretzero.fitsure.model.dto.InsurancePlan;
import com.pretzero.fitsure.model.dto.Payment;
import com.pretzero.fitsure.model.service.CommentService;
import com.pretzero.fitsure.model.service.CouponService;
import com.pretzero.fitsure.model.service.InsurancePlanService;
import com.pretzero.fitsure.model.service.PaymentService;
import com.pretzero.fitsure.util.JwtUtil;
import com.pretzero.fitsure.util.SessionUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/insurance") // 사용자를 위한 insuranceController 
public class InsuranceController {
	
	@Autowired
	InsurancePlanService insuranceService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	CouponService couponService;
	
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
	@PostMapping("/{insuranceId}/pay")
	public String payInsurance(@PathVariable int insuranceId, @RequestParam(required = false) String couponCode, HttpServletRequest request){
		String token = request.getHeader("access-token").substring(7); 
		int userId = JwtUtil.getuserId(token);
		
		if (userId < 0 || JwtUtil.isExpired(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
		
		Payment paytemp = new Payment();
		paytemp.setUserId(userId);
		paytemp.setInsuranceId(insuranceId);
		
		
		InsurancePlan insurance = insuranceService.readInsurance(insuranceId);
		
		double originalAmount = insurance.getPremium();
		double discountAmount = 0.0;
		
		// 쿠폰 사용하는 경우, 5% 할인을 위하여 
		if (couponCode != null && !couponCode.isEmpty()) {
	        Coupon coupon = couponService.findCoupon(userId); // 쿠폰 조회

	        if (coupon == null || coupon.getUsed() == 0) {
	        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired coupon");
	        }
	        
	        // 할인 계산 (예: 5% 할인)
	        discountAmount = originalAmount * 0.05;
	        originalAmount -= discountAmount; // 최종 결제 금액 계산
	        couponService.useCoupon(userId);
	    }
		
		paytemp.setAmount(originalAmount); // 적용된 쿠폰 코드 저장
		
		paymentService.createPayment(paytemp);
		
		 // 세션에 결제 정보 저장
	    SessionUtils.addAttribute("payment", paytemp);

	    // payReady로 리다이렉트
	    return "redirect:/pay/ready";

		
	}
	
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
