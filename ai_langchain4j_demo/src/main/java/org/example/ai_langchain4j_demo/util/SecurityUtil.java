package org.example.ai_langchain4j_demo.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 安全工具类，用于获取当前登录用户信息
 */
public class SecurityUtil {
    
    private static final String USER_ID_KEY = "currentUserId";
    private static final String USER_ACCOUNT_KEY = "currentUserAccount";
    
    /**
     * 获取当前登录用户ID
     */
    public static Integer getCurrentUserId() {
        HttpSession session = getSession();
        if (session != null) {
            Object userId = session.getAttribute(USER_ID_KEY);
            return userId != null ? (Integer) userId : null;
        }
        return null;
    }
    
    /**
     * 获取当前登录用户账号
     */
    public static String getCurrentUserAccount() {
        HttpSession session = getSession();
        if (session != null) {
            Object userAccount = session.getAttribute(USER_ACCOUNT_KEY);
            return userAccount != null ? (String) userAccount : null;
        }
        return null;
    }
    
    /**
     * 设置当前登录用户信息
     */
    public static void setCurrentUser(Integer userId, String userAccount) {
        HttpSession session = getSession();
        if (session != null) {
            session.setAttribute(USER_ID_KEY, userId);
            session.setAttribute(USER_ACCOUNT_KEY, userAccount);
        }
    }
    
    /**
     * 清除当前登录用户信息
     */
    public static void clearCurrentUser() {
        HttpSession session = getSession();
        if (session != null) {
            session.removeAttribute(USER_ID_KEY);
            session.removeAttribute(USER_ACCOUNT_KEY);
        }
    }
    
    private static HttpSession getSession() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getSession(false);
        }
        return null;
    }
}