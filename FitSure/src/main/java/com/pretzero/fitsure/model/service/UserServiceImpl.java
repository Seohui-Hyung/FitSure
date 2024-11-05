package com.pretzero.fitsure.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pretzero.fitsure.model.dao.UserDao;
import com.pretzero.fitsure.model.dto.User;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	// 회원 로그인 
	public User login(String userLoginId, String password) {
		Map<String, String> info = new HashMap<>();
		
		info.put("id", userLoginId);
		info.put("password", password);
		
		User tmp = userDao.selectOne(info);
		
		return tmp;
	}
	
	// 회원 회원가입
	public void signup (User user) {
	}
	
	
}
