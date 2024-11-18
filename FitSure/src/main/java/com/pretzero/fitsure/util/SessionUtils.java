package com.pretzero.fitsure.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class SessionUtils {

    public static void addAttribute(String name, Object value) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalStateException("No RequestAttributes found in the current context");
        }
        requestAttributes.setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    public static String getStringAttributeValue(String name) {
        Object value = getAttribute(name);
        return value == null ? null : String.valueOf(value);
    }

    public static Object getAttribute(String name) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalStateException("No RequestAttributes found in the current context");
        }
        return requestAttributes.getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }
}
