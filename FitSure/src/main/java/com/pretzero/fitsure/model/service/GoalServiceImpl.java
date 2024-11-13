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
		int list = findGoal(user_id);
		
		if(list == 0) {
			return goalDao.insertGoal(user_id);
		} else {
			return 0;
		}
	}

	@Override
	public int findGoal(int user_id) {
		int result = goalDao.selectGoal(user_id);
		
		return result;
	}

	@Override
	public void incrementWeekGoal() {
		goalDao.addWeekGoal();
		
	}

	

}
