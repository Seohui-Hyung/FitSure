package com.pretzero.fitsure.model.service;

import java.util.List;

import com.pretzero.fitsure.model.dto.Comment;

public interface CommentService {

	public List<Comment> getCommentList(int insuranceId);

	public boolean writeComment(Comment comment);

	public boolean deleteComment(int insuranceId, int commentId);

	public int findWriter(int insuranceId, int commentId);

}
