package com.pretzero.fitsure.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pretzero.fitsure.model.dao.CommentDao;
import com.pretzero.fitsure.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentDao commentDao;

	@Override
	public List<Comment> getCommentList(int insuranceId) {
		
		return commentDao.getList(insuranceId);
	}

	@Override
	public boolean writeComment(Comment comment) {
		return commentDao.insertComment(comment) == 1;
	}

	@Override
	public boolean deleteComment(int insuranceId, int commentId) {
		Comment com = new Comment();
		com.setInsuranceId(insuranceId);
		com.setCommentId(commentId);
		
		return commentDao.deleteComment(com) == 1;
	}

}
