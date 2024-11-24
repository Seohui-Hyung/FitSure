package com.pretzero.fitsure.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.UserDao;
import com.pretzero.fitsure.model.dto.SearchCondition;
import com.pretzero.fitsure.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	// 회원 로그인 
	public User login(String userLoginId, String password) {
		Map<String, String> info = new HashMap<>();
		
		info.put("userLoginId", userLoginId);
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

	@Override
	public boolean deleteUser(String userLoginId) {
		if (userDao.deleteUser(userLoginId) > 0) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public boolean checkPassword(int userId, String password) {
		
		Map<String, String> info = new HashMap<>();
		
		info.put("userId", String.valueOf(userId));
		info.put("password", password);
		
		int result = userDao.checkPassword(info);		
		
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getPassword(String loginId) {
		return userDao.getPassword(loginId);
	}


	@Override
	public String findUserId(User user) {
		return userDao.findUserId(user);
	}

	@Override
	public List<User> getUserList() {
		return userDao.selectUserList();
		
	}

	@Override
	public User userInfo(String userLoginId) {
		return userDao.selectUser(userLoginId);
	}

	@Override
	public boolean updateUserInfo(User existingUser) {
		int result = userDao.updateUser(existingUser);
		return result > 0 ? true : false;
		
	}


	@Override
	public boolean updatePassword(String userLoginId, int newPassword) {
		User user = userDao.selectUser(userLoginId);
		String newp = newPassword+"";
		user.setPassword(newp);
		return userDao.updatePassword(user) > 0 ? true : false;
	}

	@Override
	public List<User> search(SearchCondition condition) {
		
		return userDao.searchUser(condition);
	}

	@Override
	public User findUserName(int userId) {
		
		return userDao.getUserName(userId);
	}

	
	
	
	
}
