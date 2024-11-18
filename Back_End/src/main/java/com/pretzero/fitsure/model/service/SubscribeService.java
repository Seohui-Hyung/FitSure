package com.pretzero.fitsure.model.service;

import com.pretzero.fitsure.model.dto.Payment;

public interface SubscribeService {

	boolean findSub(Payment payment);

	void addSub(Payment payment);

}
