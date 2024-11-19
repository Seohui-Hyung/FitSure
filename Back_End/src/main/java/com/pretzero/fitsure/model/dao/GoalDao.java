package com.pretzero.fitsure.model.dao;

import java.util.List;

import com.pretzero.fitsure.model.dto.Goal;

public interface GoalDao {

	int selectGoal(int user_id);

	int insertGoal(int user_id);

	void addWeekGoal();

	int checkGoal(int userId);

	int changeCoupon(int userId);

	int cancelGoal(int userId);

	Goal selectGoalList(int userId);

}
