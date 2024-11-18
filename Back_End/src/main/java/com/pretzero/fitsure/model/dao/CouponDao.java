package com.pretzero.fitsure.model.dao;

import com.pretzero.fitsure.model.dto.Coupon;

public interface CouponDao {

	int awardedCoupon(int userId);

	Coupon selectCoupon(int userId);

	void usedCoupon(int userId);

}
