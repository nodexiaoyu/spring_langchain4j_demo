package org.example.ai_langchain4j_demo.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class IpUtil {
    
    /**
     * 获取IP地址
     * 使用Nginx等反向代理软件时，则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理，则X-Forwarded-For的值不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (!isValidIp(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (!isValidIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (!isValidIp(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (!isValidIp(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (!isValidIp(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            // 忽略异常
        }
        
        // 使用代理，则获取第一个IP地址
        if (StringUtils.hasLength(ip) && ip.length() > 15 && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        
        return ip;
    }
    
    private static boolean isValidIp(String ip) {
        return StringUtils.hasLength(ip) && !"unknown".equalsIgnoreCase(ip);
    }
}