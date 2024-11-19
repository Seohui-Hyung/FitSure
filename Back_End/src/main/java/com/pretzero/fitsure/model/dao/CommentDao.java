package com.pretzero.fitsure.model.dao;

import java.util.List;

import com.pretzero.fitsure.model.dto.Comment;

public interface CommentDao {

	public List<Comment> getList(int insuranceId);

	public int insertComment(Comment comment);

	public int deleteComment(Comment com);

	public int findUserId(Comment com);

}
