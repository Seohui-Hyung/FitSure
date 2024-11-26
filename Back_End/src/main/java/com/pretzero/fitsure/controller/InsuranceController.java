package com.pretzero.fitsure.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pretzero.fitsure.model.dto.Comment;
import com.pretzero.fitsure.model.dto.InsurancePlan;
import com.pretzero.fitsure.model.dto.Payment;
import com.pretzero.fitsure.model.dto.paymenttest.paymentjson;
import com.pretzero.fitsure.model.service.CommentService;
import com.pretzero.fitsure.model.service.CouponService;
import com.pretzero.fitsure.model.service.InsurancePlanService;
import com.pretzero.fitsure.model.service.PaymentService;
import com.pretzero.fitsure.util.JwtUtil;
import com.pretzero.fitsure.util.SessionUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
	
	@Autowired
	HttpSession session;
	
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
	public ResponseEntity<?> detailInsurance(@PathVariable int insuranceId) {
	    InsurancePlan insurancePlan = insuranceService.readInsurance(insuranceId);

	    return insurancePlan == null
			    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No coupon found.") 
			    : ResponseEntity.ok(insurancePlan); 
	}

	
	// 보험 결제의 경우 PayController로 별도 진행 
	@PostMapping("/{insuranceId}/pay")
	public void payInsurance(@PathVariable int insuranceId, @RequestBody paymentjson paymentjson, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String token = request.getHeader("access-token"); 
		int userId = JwtUtil.getuserId(token);
		
		if (userId < 0 || JwtUtil.isExpired(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
		
		Payment paytemp = new Payment();
		paytemp.setUserId(userId);
		paytemp.setInsuranceId(insuranceId);
		System.out.println(paymentjson.getFinalAmount());
		
		InsurancePlan insurance = insuranceService.readInsurance(insuranceId);
		
		double originalAmount = insurance.getPremium();

		
		// 쿠폰 사용하는 경우, 5% 할인을 위하여 
		if (paymentjson.getCouponCode() != 0) {
	        couponService.useCoupon(paymentjson.getCouponCode());
	    }
		
		paytemp.setAmount(paymentjson.getFinalAmount()); // 적용된 쿠폰 코드 저장
		System.out.println(paymentjson.getFinalAmount());
		paymentService.createPayment(paytemp);
		
		
		 // 세션에 결제 정보 저장
//	    SessionUtils.addAttribute("payment", paytemp);
		session.setAttribute("payment", paytemp);
		
	    response.sendRedirect("/pay/ready");

		
	}
	
	// 보험 댓글 작성 
	@PostMapping("/comment/{insuranceId}")
	public ResponseEntity<?> commentInsurance(@PathVariable int insuranceId, @RequestBody Comment comment, HttpServletRequest request){
		String token = request.getHeader("access-token"); 
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
	
	
	// 보험 댓글 삭제 
	@DeleteMapping("/comment/{insuranceId}/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int insuranceId, @PathVariable int commentId,
			HttpServletRequest request) {
		String token = request.getHeader("access-token");
		int userId = JwtUtil.getuserId(token);

		if (userId < 0 || JwtUtil.isExpired(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
		}

		int writer = commentService.findWriter(insuranceId, commentId);

		if (userId == writer) {
			commentService.deleteComment(insuranceId, commentId);
			return ResponseEntity.status(HttpStatus.CREATED).body("Comment deleted successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add comment");
	}
	

	
	
	
	
	
}
