package com.pretzero.fitsure.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.pretzero.fitsure.model.dto.paymenttest.ApproveResponse;
import com.pretzero.fitsure.model.dto.paymenttest.ReadyResponse;

@Service
public class KaKaoPayService {

	private static final String ACCESS_TOKEN = "DEV1D95D3711948177A7048BE35C1E921868C71F";

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "SECRET_KEY " + ACCESS_TOKEN);
		headers.set("Content-type", "application/json");
		return headers;
	}

	public ReadyResponse payReady(String name, int totalPrice) {
		// 파라미터를 MultiValueMap으로 구성
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", "TC0ONETIME");
		parameters.add("partner_order_id", "1234567890");
		parameters.add("partner_user_id", "512CF2253D4B20DD8936");
		parameters.add("item_name", name);
		parameters.add("quantity", "1");
		parameters.add("total_amount", String.valueOf(totalPrice));
		parameters.add("tax_free_amount", "0");
		parameters.add("approval_url", "http://localhost:8080/pay/completed");
		parameters.add("cancel_url", "http://localhost:8080/pay/cancel");
		parameters.add("fail_url", "http://localhost:8080/pay/fail");
		// HttpEntity로 요청 생성
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
		RestTemplate template = new RestTemplate();
		String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
		try {
			// POST 요청
			System.out.println("통");
			ResponseEntity<ReadyResponse> responseEntity = template.postForEntity(url, requestEntity,
					ReadyResponse.class);
			return responseEntity.getBody();
		} catch (Exception e) {
			System.out.println("카카오페이 준비 요청 오류: " + e.getMessage());
			throw e;
		}
	}

	// 카카오페이 결제 승인
	// 사용자가 결제 수단을 선택하고 비밀번호를 입력해 결제 인증을 완료한 뒤,
	// 최종적으로 결제 완료 처리를 하는 단계
	public ApproveResponse payApprove(String tid, String pgToken) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("cid", "TC0ONETIME"); // 가맹점 코드(테스트용)
		parameters.put("tid", tid); // 결제 고유번호
		parameters.put("partner_order_id", "1234567890"); // 주문번호
		parameters.put("partner_user_id", "512CF2253D4B20DD8936"); // 회원 아이디
		parameters.put("pg_token", pgToken); // 결제승인 요청을 인증하는 토큰

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		RestTemplate template = new RestTemplate();
		String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
		ApproveResponse approveResponse = template.postForObject(url, requestEntity, ApproveResponse.class);
		System.out.println("결제승인 응답객체: " + approveResponse);

		return approveResponse;
	}

}
