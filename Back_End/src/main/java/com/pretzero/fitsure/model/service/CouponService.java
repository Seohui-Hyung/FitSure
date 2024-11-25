package com.pretzero.fitsure.model.service;

import java.util.List;

import com.pretzero.fitsure.model.dto.Coupon;

public interface CouponService {

	boolean awardCoupon(int userId);

	Coupon findCoupon(int userId);

	void useCoupon(int couponId);

	List<Coupon> getCouponList(int userId);

	List<Coupon> selectUnused(int userId);

}
