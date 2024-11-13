package com.pretzero.fitsure.model.dao;

import com.pretzero.fitsure.model.dto.InsurancePlan;

public interface InsurancePlanDao {

	void registInsurance(InsurancePlan insurancePlan);

	int stopInsurance(int insuranceId);

	int changeInsurance(InsurancePlan insurancePlan);

	InsurancePlan selectInsurance(int insuranceId);

}
