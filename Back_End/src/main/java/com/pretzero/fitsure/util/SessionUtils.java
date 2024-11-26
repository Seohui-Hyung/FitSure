package com.pretzero.fitsure.util;

import java.util.Objects;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

public class SessionUtils {
	
	private SessionUtils sessionUtils = new SessionUtils();

	private SessionUtils() {}
	
	public SessionUtils getInstance() {
		return sessionUtils;
	}
	
	
//    public static void addAttribute(String name, Object value) {
//        Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
//    }

    public static String getStringAttributeValue(String name) {
        return String.valueOf(getAttribute(name));
    }

//    public static Object getAttribute(String name) {
//        return Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute(name, RequestAttributes.SCOPE_SESSION);
//    }
    
    public static void addAttribute(String name, Object value) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getSession();
        if (session != null) {
            session.setAttribute(name, value);
        } else {
            throw new IllegalStateException("No session found");
        }
    }

    public static Object getAttribute(String name) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getSession();
        if (session != null) {
            return session.getAttribute(name);
        } else {
            return null;
        }
    }
}