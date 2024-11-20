package com.pretzero.fitsure.model.dao;

import java.util.Map;

import com.pretzero.fitsure.model.dto.Admin;

public interface AdminDao {
	
	public Admin selectOne(Admin admin);

	public String selectManager(int adminId);
}
