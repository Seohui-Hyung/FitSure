package com.pretzero.fitsure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pretzero.fitsure.model.service.MailService;
import com.pretzero.fitsure.model.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MailController {

	@Autowired
	private MailService mailService;
	private int number;

	// 인증 이메일 전송
	@PostMapping("/mailSend")
	public HashMap<String, Object> mailSend(@RequestParam String mail) {
		HashMap<String, Object> map = new HashMap<>();
		System.out.println("done");

		try {
			number = mailService.sendMail(mail);
			String num = String.valueOf(number);

			map.put("success", true);
			map.put("number", num);
		} catch (Exception e) {
			map.put("success", false);
			map.put("error", e.getMessage());
		}

		return map;
	}

	// 인증번호 일치여부 확인
	@GetMapping("/mailCheck")
	public ResponseEntity<Boolean> mailCheck(@RequestParam String userNumber) {
		boolean isMatch = userNumber.equals(String.valueOf(number));
		return ResponseEntity.ok(isMatch);
	}
	
}
