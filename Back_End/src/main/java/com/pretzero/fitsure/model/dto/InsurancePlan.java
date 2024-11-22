package com.pretzero.fitsure.model.dto;


public class InsurancePlan {
    private int insuranceId;
    private int insuranceType;
    private String insuranceName;
    private int adminId;
    private int premium;
    private String detail;
    private int validity;
    
    private String fileUrl;
    

	public InsurancePlan() {	}
	
	public InsurancePlan(int insuranceId, int insuranceType, String insuranceName, int adminId, int premium, String detail, int validity, String fileUrl) {
		super();
		this.insuranceId = insuranceId;
		this.insuranceType = insuranceType;
		this.insuranceName = insuranceName;
		this.adminId = adminId;
		this.premium = premium;
		this.detail = detail;
		this.validity = validity;
		this.fileUrl = fileUrl;
	}

	public int getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(int insuranceId) {
		this.insuranceId = insuranceId;
	}

	public int getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(int insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public int getPremium() {
		return premium;
	}

	public void setPremium(int premium) {
		this.premium = premium;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}




}
