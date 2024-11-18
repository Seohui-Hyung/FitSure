package com.pretzero.fitsure.model.service;

import java.util.List;

import com.pretzero.fitsure.model.dto.Notice;

public interface NoticeService {
	
	void writenotice(Notice notice);
	
	boolean removeNotice(int noticeId);
	
	boolean modifyNotice(Notice notice);
	
	Notice readNotice(int noticeId);
	
	List<Notice> getNoticeList();
	
}
