package com.pretzero.fitsure.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.UserDao;
import com.pretzero.fitsure.model.dto.User;

@Service
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
	public int signup (User user) {
		if(emailCheck(user.getEmail())) {
			userDao.insertUser(user);
			return 1;
		} else {
			return 0;
		}
	}
	

	@Override
	public boolean emailCheck(String email) {
		if(userDao.checkValidEmail(email) == 0) return true;
		else return false;
	}
	
	
}
