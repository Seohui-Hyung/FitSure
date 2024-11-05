package com.pretzero.fitsure.model.dto;

import java.util.Date;

public class Subscribe {
    private int subscribeId;
    private int userId;
    private int insuranceId;
    private String detail;
    private double discountRate;
    private Date discountValidate;
    
    
    
	public Subscribe() {	}



	public Subscribe(int subscribeId, int userId, int insuranceId, String detail, double discountRate,
			Date discountValidate) {
		super();
		this.subscribeId = subscribeId;
		this.userId = userId;
		this.insuranceId = insuranceId;
		this.detail = detail;
		this.discountRate = discountRate;
		this.discountValidate = discountValidate;
	}



	public int getSubscribeId() {
		return subscribeId;
	}



	public void setSubscribeId(int subscribeId) {
		this.subscribeId = subscribeId;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getInsuranceId() {
		return insuranceId;
	}



	public void setInsuranceId(int insuranceId) {
		this.insuranceId = insuranceId;
	}



	public String getDetail() {
		return detail;
	}



	public void setDetail(String detail) {
		this.detail = detail;
	}



	public double getDiscountRate() {
		return discountRate;
	}



	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}



	public Date getDiscountValidate() {
		return discountValidate;
	}



	public void setDiscountValidate(Date discountValidate) {
		this.discountValidate = discountValidate;
	}

	
	
}
