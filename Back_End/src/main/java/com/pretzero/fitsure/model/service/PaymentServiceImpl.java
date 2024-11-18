package com.pretzero.fitsure.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.PaymentDao;
import com.pretzero.fitsure.model.dto.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao paymentDao;

	@Override
	public void createPayment(Payment paytemp) {
		paymentDao.insertPay(paytemp);
	}

}
