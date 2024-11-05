package com.pretzero.fitsure.model.dto;

import java.util.Date;

public class Payment {
    private int paymentId;
    private int userId;
    private int insuranceId;
    private double amount;
    private Date paymentDate;
    private boolean refund;
    
    
    
    
	public Payment() {	}




	public Payment(int paymentId, int userId, int insuranceId, double amount, Date paymentDate, boolean refund) {
		super();
		this.paymentId = paymentId;
		this.userId = userId;
		this.insuranceId = insuranceId;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.refund = refund;
	}




	public int getPaymentId() {
		return paymentId;
	}




	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
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




	public double getAmount() {
		return amount;
	}




	public void setAmount(double amount) {
		this.amount = amount;
	}




	public Date getPaymentDate() {
		return paymentDate;
	}




	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}




	public boolean isRefund() {
		return refund;
	}




	public void setRefund(boolean refund) {
		this.refund = refund;
	}


}

