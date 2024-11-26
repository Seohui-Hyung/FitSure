package com.pretzero.fitsure.model.service;

import java.util.List;

import com.pretzero.fitsure.model.dto.Payment;
import com.pretzero.fitsure.model.dto.Subscribe;

public interface SubscribeService {

	boolean findSub(Payment payment);

	void addSub(Payment payment);
	
	List<Subscribe> ongoingSub(int userId);

}
