package com.pretzero.fitsure.model.dto;

import java.util.Date;

public class Notice {
    private int noticeId;
    private int adminId;
    private String title;
    private String content;
    private Date regDate;
    private int viewCnt;
    private String manager;
    
    
	public Notice() {	}


	public Notice(int noticeId, int adminId, String title, String content, Date regDate, String manager, int viewCnt) {
		super();
		this.noticeId = noticeId;
		this.adminId = adminId;
		this.title = title;
		this.content = content;
		this.regDate = regDate;
		this.manager = manager;
		this.viewCnt = viewCnt;
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public int getViewCnt() {
		return viewCnt;
	}


	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	
}

