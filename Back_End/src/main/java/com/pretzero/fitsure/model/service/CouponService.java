package com.pretzero.fitsure.model.service;

import com.pretzero.fitsure.model.dto.Coupon;

public interface CouponService {

	boolean awardCoupon(int userId);

	Coupon findCoupon(int userId);

	void useCoupon(int userId);

}
