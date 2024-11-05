package com.pretzero.fitsure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretzero.fitsure.model.dto.Admin;
import com.pretzero.fitsure.model.service.AdminService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	// 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin admin, HttpSession session) {
        Admin loginAdmin = adminService.login(admin.getAdminName(), admin.getAdminPassword());
        if (loginAdmin != null) {
            session.setAttribute("admin", loginAdmin);
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

}
