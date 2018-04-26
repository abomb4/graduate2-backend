package org.wlyyy.itrs.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.wlyyy.itrs.domain.User;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 加密相关Utils
 *
 * @author wly
 */
public class SecurityUtils {

    /**
     * 加密明文密码。
     * 这个方法一经改变，所有用户都不能登录了。
     *
     * @param password 密码明文
     * @param salt 咸盐
     * @return SHA256密码
     */
    public static String encrypyPassword(String password, String salt) {
        return DigestUtils.sha256Hex(salt + password);
    }

    /**
     * 生成咸盐
     *
     * @return 咸盐
     */
    public static String generateSalt() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 生成用户登录Session Key
     *
     * @param user 用户对象
     * @param clientIp 客户端ip
     * @return sessionKey
     */
    public static String generateSessionKey(User user, String clientIp, LocalDateTime time) {
        final String format = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        final String toEncrypt = user.getUserName() + clientIp + format;

        final String encrypted = DigestUtils.md5Hex(toEncrypt);

        return encrypted;
    }
}
