package com.pretzero.fitsure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.pretzero.fitsure.model.service.PaymentService;
import com.pretzero.fitsure.model.service.UserService;

@RestController
public class PayController {

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UserService userService;
	
	

}
