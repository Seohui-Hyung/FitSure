package com.pretzero.fitsure.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.CouponDao;
import com.pretzero.fitsure.model.dto.Coupon;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	CouponDao couponDao;
	
	@Override
	public boolean awardCoupon(int userId) {
		int result = couponDao.awardedCoupon(userId);
		if(result != 0) return true;
		else return false;
	}

	@Override
	public Coupon findCoupon(int userId) {
		return couponDao.selectCoupon(userId);
	}

	@Override
	public void useCoupon(int userId) {
		couponDao.usedCoupon(userId);
		
	}
	

}
