package com.pretzero.fitsure.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.pretzero.fitsure.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	private final String HEADER_AUTH = "access-token";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//OPTIONS 이라고 하는 요청 메서드를 보낸다. 
		// client 가 서버에게 요청을 보내려고 했을 때, 사전 요청을 보낸다. 
		// 서버가 요청을 수락할 수 있는 상태인 건지 확인 
		if(request.getMethod().equals("OPTIONS")) return true;
		
		String token = request.getHeader(HEADER_AUTH);
		
		if(token != null) {
			jwtUtil.isExpired(token);
			return true;
		}
		
		throw new Exception("유효하지 않은 접근입니다.");
		
	}

}
