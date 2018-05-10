package org.wlyyy.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet工具类
 */
public class ServletUtils {


    public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
}
