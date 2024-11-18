package com.pretzero.fitsure.model.dto;

import java.util.Date;

public class User {
    private int userId;
    private String userLoginId;
    private String password;
    private String username;
    private String email;
    private Date birthDate;
    private String gender; // Enum("M", "F")
    private int available;
    
    public User() {} 
    
	public User(int userId, String userLoginId, String password, String username, String email, Date birthDate,
			String gender, int available) {
		super();
		this.userId = userId;
		this.userLoginId = userLoginId;
		this.password = password;
		this.username = username;
		this.email = email;
		this.birthDate = birthDate;
		this.gender = gender;
		this.available = available;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserLoginId() {
		return userLoginId;
	}
	
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public int getAvailable() {
		return available;
	}
	
	public void setAvailable(int available) {
		this.available = available;
	}

}
