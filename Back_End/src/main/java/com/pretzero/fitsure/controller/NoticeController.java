package com.pretzero.fitsure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pretzero.fitsure.model.dto.Notice;
import com.pretzero.fitsure.model.service.NoticeService;

public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	// 공지 사항 등록
	@PostMapping("/admin/notice/write")
	public ResponseEntity<String> noticeWrite(@RequestBody Notice notice) {
		try {
			noticeService.writenotice(notice);
			return ResponseEntity.ok("공지사항이 등록되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("공지사항 등록 실패: " + e.getMessage());				
		}
	}
	
	// 공지 사항 수정
	@PutMapping("/admin/notice/{notice_id}/modify")
	public ResponseEntity<String> noticeModify(@PathVariable("notice_id") int noticeId, @RequestBody Notice notice) {
		notice.setNoticeId(noticeId);
		if (noticeService.modifyNotice(notice)) {
			return ResponseEntity.ok("수정되었습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("공지사항을 찾을 수 없습니다.");				
		}
	}
	
	// 공지 사항 삭제
	@DeleteMapping("/admin/notice/{notice_id}/remove")
	public ResponseEntity<String> noticeRemove(@PathVariable("notice_id") int noticeId) {
		if (noticeService.removeNotice(noticeId)) {
			return ResponseEntity.ok("삭제되었습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("공지사항을 찾을 수 없습니다.");			
		}
	}
	
	// 공지 사항 조회 (관리자)
	@GetMapping("/admin/notice/{notice_id}")
	public ResponseEntity<Notice> morahazi(@PathVariable("notice_id") int noticeId) {
		Notice notice = noticeService.readNotice(noticeId);
		if (notice != null) {
			return ResponseEntity.ok(notice);
		} else {			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	// 공지 사항 조회 (고객)
	@GetMapping("/notice/{notice_id}")
	public ResponseEntity<Notice> morahaji(@PathVariable("notice_id") int noticeId) {
		Notice notice = noticeService.readNotice(noticeId);
		if (notice != null) {
			return ResponseEntity.ok(notice);
		} else {			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
}
