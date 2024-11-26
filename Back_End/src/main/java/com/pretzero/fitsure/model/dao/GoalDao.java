package com.pretzero.fitsure.model.dao;

import java.util.List;

import com.pretzero.fitsure.model.dto.Goal;

public interface GoalDao {

	int selectGoal(int userId);

	int insertGoal(int userId);

	void addWeekGoal();

	int checkGoal(int userId);

	int changeCoupon(int userId);

	int cancelGoal(int userId);

	Goal selectGoalList(int userId);

}
