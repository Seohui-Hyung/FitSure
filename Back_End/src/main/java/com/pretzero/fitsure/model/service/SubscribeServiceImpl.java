package com.pretzero.fitsure.model.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.InsurancePlanDao;
import com.pretzero.fitsure.model.dao.SubscribeDao;
import com.pretzero.fitsure.model.dto.Payment;
import com.pretzero.fitsure.model.dto.Subscribe;

@Service
public class SubscribeServiceImpl implements SubscribeService {

	@Autowired
	SubscribeDao subscribeDao;
	
	@Autowired
	InsurancePlanDao insurancePlanDao;
	
	@Override
	public boolean findSub(Payment payment) {
	
		Subscribe sub = new Subscribe();
		sub.setUserId(payment.getUserId());
		sub.setInsuranceId(payment.getPaymentId());
		
		return subscribeDao.selectSub(sub) != null ? false : true;
	}

	@Override
	public void addSub(Payment payment) {
		
		Subscribe sub = new Subscribe();
		sub.setUserId(payment.getUserId());
		sub.setInsuranceId(payment.getInsuranceId());
		if (payment.getAmount() < insurancePlanDao.selectInsurance(payment.getInsuranceId()).getPremium()) {
			sub.setDiscountRate(0.05);
		}
		
		subscribeDao.insertSub(sub);
		
	}

}
