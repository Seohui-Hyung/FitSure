package com.pretzero.fitsure.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pretzero.fitsure.model.dto.Goal;


public interface GoalService {

	int createGoal(int user_id);
	
	int findGoal(int user_id);

	void incrementWeekGoal();

	boolean achievedWeekGoal(int userId);

	boolean changeCoupon(int userId);

	boolean cancelGoal(int userId);

	Goal getGoalList(int userId);


}
