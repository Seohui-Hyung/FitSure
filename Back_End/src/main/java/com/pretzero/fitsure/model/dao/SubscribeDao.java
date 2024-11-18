package com.pretzero.fitsure.model.dao;

import com.pretzero.fitsure.model.dto.Subscribe;

public interface SubscribeDao {

	Subscribe selectSub(Subscribe sub);

	void insertSub(Subscribe sub);

}
