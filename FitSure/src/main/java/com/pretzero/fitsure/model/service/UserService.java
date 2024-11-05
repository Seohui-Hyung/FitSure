package com.pretzero.fitsure.model.service;

import com.pretzero.fitsure.model.dto.User;

public interface UserService {
	
	// 회원 로그인 
	public User login(String userLoginId, String password);
	
	// 회원 회원가입
	public void signup (User user);
	
	
}
