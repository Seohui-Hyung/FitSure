package com.pretzero.fitsure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pretzero.fitsure.model.dto.InsurancePlan;
import com.pretzero.fitsure.model.dto.Payment;
import com.pretzero.fitsure.model.dto.paymenttest.ApproveResponse;
import com.pretzero.fitsure.model.dto.paymenttest.ReadyResponse;
import com.pretzero.fitsure.model.service.InsurancePlanService;
import com.pretzero.fitsure.model.service.KaKaoPayService;
import com.pretzero.fitsure.util.SessionUtils;

@Controller
@RequestMapping("/pay")
public class PayController {
	
	@Autowired
    private KaKaoPayService kakaoPayService;
    
	@Autowired
    private InsurancePlanService insurancePlanService;
    
    @PostMapping("/ready")
    public @ResponseBody ReadyResponse payReady(@RequestBody Payment payment) {
    	
        int userId = payment.getUserId();
        int insurance = payment.getInsuranceId();
        String insurancename = insurancePlanService.readInsurance(insurance).getInsuranceType();
        System.out.println(insurancename);
        int totalPrice = (int) payment.getAmount();

        // 카카오 결제 준비하기
        ReadyResponse readyResponse = kakaoPayService.payReady(insurancename, totalPrice);
        // 세션에 결제 고유번호(tid) 저장
        SessionUtils.addAttribute("tid", readyResponse.getTid());
        System.out.println("결제 고유번호: " + readyResponse.getTid());

        return readyResponse;
    }

    @GetMapping("/pay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken) {
    
        String tid = SessionUtils.getStringAttributeValue("tid");
        System.out.println("결제승인 요청을 인증하는 토큰: " + pgToken);
        System.out.println("결제 고유번호: " + tid);

        // 카카오 결제 요청하기
        ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);

        return "redirect:/order/completed";
    }
}