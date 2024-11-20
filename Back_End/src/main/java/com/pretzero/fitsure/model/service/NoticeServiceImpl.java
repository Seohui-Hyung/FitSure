package com.pretzero.fitsure.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.NoticeDao;
import com.pretzero.fitsure.model.dto.Notice;
import com.pretzero.fitsure.model.dto.SearchCondition;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeDao noticeDao;
	
	@Override
	public void writenotice(Notice notice) {
		noticeDao.registNotice(notice);
	}

	@Override
	public boolean removeNotice(int noticeId) {
		int result = noticeDao.deleteNotice(noticeId);
		
		if (result > 0) return true;
		else return false;
	}

	@Override
	public boolean modifyNotice(Notice notice) {
		int result = noticeDao.changeNotice(notice);
		
		if (result > 0) return true;
		else return false;
	}

	@Override
	public Notice readNotice(int noticeId) {
		return noticeDao.selectNotice(noticeId);
	}

	@Override
	public List<Notice> getNoticeList() {
		return noticeDao.allNoticeList();
	}

	@Override
	public List<Notice> search(SearchCondition condition) {
		return noticeDao.searchNotice(condition);
	}
	
	
}
