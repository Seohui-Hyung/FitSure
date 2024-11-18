package com.pretzero.fitsure.model.dto;


import java.util.Date;

public class GoalResult {
    private int resultId;
    private int goalId;
    private int walking;
    private String attachment;
    private boolean achieve;
    private Date achieveDate;
    
    private int userId;
    
   
    public GoalResult() {}
    
    
	public GoalResult(int resultId, int goalId, int walking, String attachment, boolean achieve, Date achieveDate, int userId) {
		super();
		this.resultId = resultId;
		this.goalId = goalId;
		this.walking = walking;
		this.attachment = attachment;
		this.achieve = achieve;
		this.achieveDate = achieveDate;
		this.userId = userId;
	}
	
	
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public int getGoalId() {
		return goalId;
	}
	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}
	public int getWalking() {
		return walking;
	}
	public void setWalking(int walking) {
		this.walking = walking;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public boolean isAchieve() {
		return achieve;
	}
	public void setAchieve(boolean achieve) {
		this.achieve = achieve;
	}
	public Date getAchieveDate() {
		return achieveDate;
	}
	public void setAchieveDate(Date achieveDate) {
		this.achieveDate = achieveDate;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}

}
