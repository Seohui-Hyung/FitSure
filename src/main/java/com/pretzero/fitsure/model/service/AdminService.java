package com.pretzero.fitsure.model.service;

import com.pretzero.fitsure.model.dto.Admin;

public interface AdminService {
	
	// 로그인하기 
	public Admin login(String adminName, String adminPassword);
}
