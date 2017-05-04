package com.wayne.interceptor;

import com.wayne.common.DESUtils;
import com.wayne.common.WebUtils;
import com.wayne.config.Const;
import com.wayne.model.BbsUser;
import com.wayne.service.BbsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class UserInterceptorHandler extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private BbsUserService bbsUserService;

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

		/*
		 * 没有加@Auth 注解的就不需要过拦截器了
		 */
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();
		if (!clazz.isAnnotationPresent(Auth.class) &&
				!method.isAnnotationPresent(Auth.class)) {
				return true;
		}

		/*
			拦截器中无法注入service 手动加载 bbsUserService对应在factory 里面的bean name 为bbsUserServiceImpl
		 */
		if (bbsUserService == null) {
			BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
			bbsUserService = (BbsUserService) factory.getBean("bbsUserServiceImpl");
		}

		boolean filter = false;

        BbsUser user = currentUser(request,response);
        if(null != user){
        	filter = true;
		}

		//处理非ajax请求才可以跳转 ajax请求拦截的需要js相应改动
		if(!filter){
			logger.info("用户未登录:"+request.getRequestURL().toString());
			response.sendRedirect("/bbs/index/1.html?referer="+java.net.URLEncoder.encode(request.getRequestURL().toString() , "UTF-8"));
		}

        return filter ;
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2,
            ModelAndView view) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

	private BbsUser currentUser(HttpServletRequest request, HttpServletResponse response) {

		BbsUser user = (BbsUser)request.getSession().getAttribute("user");
		if(user!=null){
			return user;
		}
		String cookieKey = Const.USER_COOKIE_KEY;
		// 获取cookie信息
		String userCookie = WebUtils.getCookie(request, cookieKey);
		// 1.cookie为空，直接清除
		if (StringUtils.isEmpty(userCookie)) {
			WebUtils.removeCookie(response, cookieKey);
			return null;
		}
		// 2.解密cookie
		String cookieInfo = null;
		// cookie 私钥
		String secret = Const.USER_COOKIE_SECRET;
		try {
			cookieInfo = DESUtils.decryptString(userCookie);
		} catch (RuntimeException e) {
			// ignore
		}
		// 3.异常或解密问题，直接清除cookie信息
		if (StringUtils.isEmpty(cookieInfo)) {
			WebUtils.removeCookie(response, cookieKey);
			return null;
		}
		String[] userInfo = cookieInfo.split("~");
		// 4.规则不匹配
		if (userInfo.length < 3) {
			WebUtils.removeCookie(response, cookieKey);
			return null;
		}
		String userId   = userInfo[0];
		String oldTime  = userInfo[1];
		String maxAge   = userInfo[2];
		// 5.判定时间区间，超时的cookie清理掉
		if (!"-1".equals(maxAge)) {
			long now  = System.currentTimeMillis();
			long time = Long.parseLong(oldTime) + (Long.parseLong(maxAge) * 1000);
			if (time <= now) {
				WebUtils.removeCookie(response, cookieKey);
				return null;
			}
		}
		if(userId == null || "null".equals(userId)){
			WebUtils.removeCookie(response, cookieKey);
			return null;
		}
		user =  bbsUserService.getUser(Integer.valueOf(userId));
		request.getSession().setAttribute("user", user);
		return user;
	}



}