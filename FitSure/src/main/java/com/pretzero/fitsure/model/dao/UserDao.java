package com.pretzero.fitsure.model.dao;

import java.util.Map;

import com.pretzero.fitsure.model.dto.User;

public interface UserDao {
	
	public User selectOne(Map<String, String> info);
	
	public int checkValidEmail (String email);
	
	public int insertUser(User user);
	
	public int deleteUser(String userLoginId);

	public int checkPassword(Map<String, String> info);

	public String getPassword(String loginId);

}
