package com.wayne.common;

import com.wayne.config.Const;
import com.wayne.model.BbsUser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Web相关工具类,同时用spring session，因此用户信息也放到session 和cookie里
 * 
 */
@Service
public class WebUtils {

	/**
	 * 用户登陆状态维持
	 * 
	 * cookie设计为: des(私钥).encode(userId~time~maxAge~ip)
	 * @param request
	 * @param response
	 * @param user
	 * @param remember   是否记住密码、此参数控制cookie的 maxAge，默认为-1（只在当前会话）<br>
	 *                   记住密码默认为30天
	 * @return void
	 */
	public static void loginUser(HttpServletRequest request, HttpServletResponse response, BbsUser user, boolean... remember) {
		
		request.getSession(true).setAttribute(Const.USER_SESSION_KEY, user);
		// 获取用户的id、nickName
		String uid     = user.getId()+"";
		// 当前毫秒数
		long   now      = System.currentTimeMillis();
		// 超时时间
		int    maxAge   = -1;
		if (remember.length > 0 && remember[0]) {
			maxAge      = 60 * 60 * 24 * 30; // 30天
		}
		// 用户id地址
		String ip		= getIP(request);
		// 构造cookie
		StringBuilder cookieBuilder = new StringBuilder()
			.append(uid).append("~")
			.append(now).append("~")
			.append(maxAge).append("~")
			.append(ip);

		// 加密cookie
		String userCookie = DESUtils.encryptString(cookieBuilder.toString());
		String cookieKey = Const.USER_COOKIE_KEY;
		// 设置用户的cookie、 -1 维持成session的状态
		setCookie(response, cookieKey, userCookie, maxAge);
	}




	/**
	 * 读取cookie
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 清除 某个指定的cookie
	 * @param response
	 * @param key
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		setCookie(response, key, null, 0);
	}

	/**
	 * 设置cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAgeInSeconds
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAgeInSeconds);
		// 指定为httpOnly保证安全性
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Requested-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}


	/**
	 * 退出即删除用户信息
	 */
	public static void logoutUser(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		removeCookie(response, Const.USER_COOKIE_KEY);
	}


	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean isAdmin(HttpServletRequest request,HttpServletResponse response) {
		BbsUser user = currentUser(request,  response);
		if(user==null){
			throw new RuntimeException("未登陆用户");
		}
		return "admin".equals(user.getUserName());

	}

	public static BbsUser currentUser(HttpServletRequest request, HttpServletResponse response) {

		BbsUser user = (BbsUser)request.getSession().getAttribute("user");
		if(user!=null){
			return user;
		}
		return null;
	}

	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
		if("XMLHttpRequest".equalsIgnoreCase(header)){
//			当前请求为Ajax请求
			return Boolean.TRUE;
		}
//		当前请求非Ajax请求
		return Boolean.FALSE;
	}

}
