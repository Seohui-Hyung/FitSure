package com.pretzero.fitsure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretzero.fitsure.model.dto.InsurancePlan;
import com.pretzero.fitsure.model.service.InsurancePlanService;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {
	
	@Autowired
	InsurancePlanService insuranceService;
	
	// 보험 전체 목 조회 
	@GetMapping("/")
	public ResponseEntity<?> insuranceList(){
		List<InsurancePlan> list = insuranceService.getInsuranceList();
		if (list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<InsurancePlan>>(list, HttpStatus.OK);
	}
	
	// 보험 상세 내역 조회 
	@GetMapping("/{insuranceId}")
	public ResponseEntity<InsurancePlan> detailInsurance(@PathVariable int insuranceId){
		InsurancePlan insurancePlan = insuranceService.readInsurance(insuranceId);
		if (insurancePlan != null)
			return new ResponseEntity<InsurancePlan>(insurancePlan, HttpStatus.OK);
		return new ResponseEntity<InsurancePlan>(HttpStatus.NOT_FOUND);
	}
	
	
	
}
