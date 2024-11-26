package com.pretzero.fitsure.model.dao;

import java.util.List;

import com.pretzero.fitsure.model.dto.Subscribe;

public interface SubscribeDao {

	Subscribe selectSub(Subscribe sub);

	void insertSub(Subscribe sub);
	
	List<Subscribe> selectIns(int userId);

}
