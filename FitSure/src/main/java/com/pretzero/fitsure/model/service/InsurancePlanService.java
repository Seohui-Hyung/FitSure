package com.pretzero.fitsure.model.service;

import java.util.List;

import com.pretzero.fitsure.model.dto.InsurancePlan;

public interface InsurancePlanService {

	void addinsurance(InsurancePlan insurancePlan);

	boolean disableInsurance(int insuranceId);

	boolean modifyInsurance(InsurancePlan insurancePlan);

	InsurancePlan readInsurance(int insuranceId);

	List<InsurancePlan> getInsuranceList();

}
