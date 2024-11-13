package com.pretzero.fitsure.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.GoalResultDao;
import com.pretzero.fitsure.model.dto.GoalResult;

@Service
public class GoalResultServiceImpl implements GoalResultService {

	@Autowired
	GoalResultDao goalResultDao;
	
	@Override
	public int completeGoal(GoalResult goalResult) {
		return goalResultDao.doneGoal(goalResult);
	}

}
