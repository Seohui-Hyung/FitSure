package com.pretzero.fitsure.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.AdminDao;
import com.pretzero.fitsure.model.dto.Admin;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private  AdminDao adminDao;
	
	// 로그인하기
	public Admin login(String adminName, String adminPassword) {
		Map<String, String> info = new HashMap<>();

		info.put("adminName", adminName);
		info.put("password", adminPassword);

		Admin tmp = adminDao.selectOne(info);
		return tmp;
	}

	@Override
	public String getName(int adminId) {
		return adminDao.selectManager(adminId);
	}
}
