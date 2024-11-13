package com.pretzero.fitsure.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface GoalService {

	int createGoal(int user_id);
	
	int findGoal(int user_id);

	void incrementWeekGoal();


}
