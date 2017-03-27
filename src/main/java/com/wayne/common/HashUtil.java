package com.wayne.common;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author TAO
 * @Date 2017/3/24 17:55
 */
public class HashUtil {
    private HashUtil() {
    }

    public static String generatePwd(String pwd) {
        String salt = generateSalt(pwd);
        return DigestUtils.sha1Hex(pwd + salt);
    }

    private static String generateSalt(String pwd) {
        return DigestUtils.md2Hex(pwd).substring(0, 8);
    }
}