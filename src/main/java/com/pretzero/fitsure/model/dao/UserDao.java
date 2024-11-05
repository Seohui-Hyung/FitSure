package com.pretzero.fitsure.model.dao;

import java.util.Map;

import com.pretzero.fitsure.model.dto.User;

public interface UserDao {
	
	public User selectOne(Map<String, String> info);

}
