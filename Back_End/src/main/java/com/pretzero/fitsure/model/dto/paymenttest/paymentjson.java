package com.pretzero.fitsure.model.dto.paymenttest;

public class paymentjson {

	private int couponCode;
	private int finalAmount;
	
	public paymentjson() {}
	
	public paymentjson(int couponCode, int finalAmount) {
		this.couponCode = couponCode;
		this.finalAmount = finalAmount;
	}

	public int getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(int couponCode) {
		this.couponCode = couponCode;
	}

	public int getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(int finalAmount) {
		this.finalAmount = finalAmount;
	}
	
	
}
