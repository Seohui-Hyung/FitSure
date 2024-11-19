package com.pretzero.fitsure.model.dto;

import java.util.Date;

public class Notice {
    private int noticeId;
    private int adminId;
    private int importance;
    private String content;
    private Date regDate;
    private String manager;
    
    
	public Notice() {	}


	public Notice(int noticeId, int adminId, int importance, String content, Date regDate, String manager) {
		super();
		this.noticeId = noticeId;
		this.adminId = adminId;
		this.importance = importance;
		this.content = content;
		this.regDate = regDate;
		this.manager = manager;
	}


	public int getNoticeId() {
		return noticeId;
	}


	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}


	public int getAdminId() {
		return adminId;
	}


	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}


	public int getImportance() {
		return importance;
	}


	public void setImportance(int importance) {
		this.importance = importance;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getRegDate() {
		return regDate;
	}


	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}


	public String getManager() {
		return manager;
	}


	public void setManager(String manager) {
		this.manager = manager;
	}

	
}

