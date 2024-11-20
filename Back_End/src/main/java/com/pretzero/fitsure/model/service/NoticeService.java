package com.pretzero.fitsure.model.service;

import java.util.List;

import com.pretzero.fitsure.model.dto.Notice;
import com.pretzero.fitsure.model.dto.SearchCondition;
import com.pretzero.fitsure.model.dto.User;

public interface NoticeService {
	
	void writenotice(Notice notice);
	
	boolean removeNotice(int noticeId);
	
	boolean modifyNotice(Notice notice);
	
	Notice readNotice(int noticeId);
	
	List<Notice> getNoticeList();

	List<Notice> search(SearchCondition condition);

	
}
