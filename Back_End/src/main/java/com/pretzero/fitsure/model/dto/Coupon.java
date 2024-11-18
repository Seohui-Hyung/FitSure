package com.pretzero.fitsure.model.dto;

import java.util.Date;

public class Coupon {
    private int couponId;
    private int userId;
    private Date couponDate;
    private boolean used;
    
    
    
	public Coupon() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Coupon(int couponId, int userId, Date couponDate, boolean used) {
		super();
		this.couponId = couponId;
		this.userId = userId;
		this.couponDate = couponDate;
		this.used = used;
	}



	public int getCouponId() {
		return couponId;
	}



	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public Date getCouponDate() {
		return couponDate;
	}



	public void setCouponDate(Date couponDate) {
		this.couponDate = couponDate;
	}



	public boolean isUsed() {
		return used;
	}



	public void setUsed(boolean used) {
		this.used = used;
	}

}

