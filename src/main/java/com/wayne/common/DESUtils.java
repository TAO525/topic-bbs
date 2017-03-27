package com.wayne.common;

import com.wayne.config.Const;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

/**
 * des加密、解密
 */
public class DESUtils {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	// 指定DES加密解密所用的密钥
	private static Key key;

	//默认生成key
	static {
		try {
			// L.cm 2015-01-20 将加密的密匙Base64
			// fix Caused by: java.security.InvalidKeyException: Wrong key size
			String desKey = Base64.encodeBase64String(Const.USER_COOKIE_SECRET.getBytes("UTF-8"));
			DESKeySpec objDesKeySpec = new DESKeySpec(desKey.getBytes("UTF-8"));
			SecretKeyFactory objKeyFactory = SecretKeyFactory.getInstance("DES");
			key = objKeyFactory.generateSecret(objDesKeySpec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private DESUtils() {
	}


	// 对字符串进行DES加密，返回BASE64编码的加密字符串
	public static final String encryptString(String str) {
		byte[] bytes = str.getBytes();
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(bytes);
			return Base64.encodeBase64URLSafeString(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 对BASE64编码的加密字符串进行解密，返回解密后的字符串
	public static final String decryptString(String str) {
		try {
			byte[] bytes = Base64.decodeBase64(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			bytes = cipher.doFinal(bytes);
			return new String(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
