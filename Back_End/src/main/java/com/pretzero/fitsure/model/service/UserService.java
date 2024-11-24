package com.pretzero.fitsure.model.service;

import java.util.List;

import com.pretzero.fitsure.model.dto.SearchCondition;
import com.pretzero.fitsure.model.dto.User;

public interface UserService {
	
	// 회원 로그인 
	public User login(String userLoginId, String password);
	
	// 회원 회원가입
	public int signup (User user);
	
	// 이메일 중복 확인 
	public boolean emailCheck(String email);
	
	// 탈퇴하기 
	public boolean deleteUser(String loginId);
	
	// 비밀번호 체크 ( 개인정보 변경 혹은, 탈퇴 시에 사용 )
	boolean checkPassword(int userId, String password);

	public String getPassword(String loginId);
	
	public String findUserId(User user);

	// 회원 목록 ( 관리자용) 
	public List<User> getUserList();

	// 회원 정보 (일반 회원용)
	public User userInfo(String userLoginId);

	// 회원 정보 업데이트
	public boolean updateUserInfo(User existingUser);

	public boolean updatePassword(String userLogionId, int newPassword);

	public List<User> search(SearchCondition condition);
	
	public User findUserName(int userId);
	
	
}
