package com.pretzero.fitsure.model.dto;


public class Comment {
    private int commentId;
    private int userId;
    private int insuranceId;
    private String content;
    
    
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Comment(int commentId, int userId, int insuranceId, String content) {
		super();
		this.commentId = commentId;
		this.userId = userId;
		this.insuranceId = insuranceId;
		this.content = content;
	}


	public int getCommentId() {
		return commentId;
	}


	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getInsuranceId() {
		return insuranceId;
	}


	public void setInsuranceId(int insuranceId) {
		this.insuranceId = insuranceId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

    // Getters and Setters
}

