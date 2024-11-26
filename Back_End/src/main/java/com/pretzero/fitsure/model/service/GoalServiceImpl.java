package com.pretzero.fitsure.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pretzero.fitsure.model.dao.GoalDao;
import com.pretzero.fitsure.model.dto.Goal;

@Service
public class GoalServiceImpl implements GoalService {

	
	@Autowired
	GoalDao goalDao;
	
	@Override
	public int createGoal(int userId) {		
		int list = findGoal(userId);
		
		if(list == 0) {
			return goalDao.insertGoal(userId);
		} else {
			return 0;
		}
	}

	@Override
	public int findGoal(int userId) {
		int result = goalDao.selectGoal(userId);
		
		return result;
	}

	@Override
	public void incrementWeekGoal() {
		goalDao.addWeekGoal();
		
	}

	@Override
	public boolean achievedWeekGoal(int userId) {
		int temp = goalDao.checkGoal(userId);
		
		if(temp == 24) return true;
		else return false;
	}

	@Override
	public boolean changeCoupon(int userId) {
		
		int result = goalDao.changeCoupon(userId);
		if(result != 0) return true;
		else return false;
		
	}

	@Override
	public boolean cancelGoal(int userId) {
		int result = goalDao.cancelGoal(userId);
		if(result != 0) return true;
		else return false;
	}

	@Override
	public Goal getGoalList(int userId) {
		Goal goalList = goalDao.selectGoalList(userId);
		return goalList;
	}
	
	

	

}
