package com.pretzero.fitsure.model.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pretzero.fitsure.model.dao.GoalDao;
import com.pretzero.fitsure.model.dao.UserDao;

@Service
public class GoalServiceImpl implements GoalService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	GoalDao goalDao;
	
	@Override
	public int createGoal(int user_id) {		
		boolean list = findGoal(user_id);
		
		if(list) {
			return goalDao.insertGoal(user_id);
		} else {
			return 0;
		}
	}

	@Override
	public boolean findGoal(int user_id) {
		int result = goalDao.selectGoal(user_id);
		
		if(result > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void incrementWeekGoal() {
		goalDao.addWeekGoal();
		
	}

	

}
