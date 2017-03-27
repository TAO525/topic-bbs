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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptorHandler extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private BbsUserService bbsUserService;

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

		/*if (bbsUserRepository == null) {//解决service为null无法注入问题
			BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
			bbsUserRepository = (BbsUserRepository) factory.getBean("bbsUserRepository");
		}*/

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

		if(!filter){
//			response.sendRedirect("/login.html?referer="+java.net.URLEncoder.encode(request.getRequestURL().toString() , "UTF-8"));
			logger.info("用户未登录");
		}
//        HandlerMethod  handlerMethod = (HandlerMethod ) handler ;
       /* Menu menu = handlerMethod.getMethod().getAnnotation(Menu.class) ;
        if(user != null || (menu!=null && menu.access()) || handlerMethod.getBean() instanceof BasicErrorController){
        	filter = true;
        }
        
        if(!filter){
        	response.sendRedirect("/login.html?referer="+java.net.URLEncoder.encode(request.getRequestURL().toString() , "UTF-8"));
        }else if(menu!=null && menu.admin() == true && !"0".equals(user.getUsertype())){	//非系统管理员 访问系统管理URL
        	response.sendRedirect("/?referer="+java.net.URLEncoder.encode(request.getRequestURL().toString() , "UTF-8"));
        }
        request.setAttribute("starttime", System.currentTimeMillis());  */
        return filter ;
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2,
            ModelAndView view) throws Exception {
    	/*if(view!=null){
	    	User user = (User) arg0.getSession().getAttribute(UKDataContext.USER_SESSION_NAME) ;
	    	if(user!=null && view!=null){
				view.addObject("user", user) ;
				view.addObject("schema",arg0.getScheme()) ;
				view.addObject("hostname",arg0.getServerName()) ;
				view.addObject("port",arg0.getServerPort()) ;
				
				view.addObject("orgi", user.getOrgi()) ;
			}
	    	HandlerMethod  handlerMethod = (HandlerMethod ) arg2 ;
			Menu menu = handlerMethod.getMethod().getAnnotation(Menu.class) ;
			if(menu!=null){
				view.addObject("subtype", menu.subtype()) ;
				view.addObject("maintype", menu.type()) ;
				view.addObject("typename", menu.name()) ;
			}
			
	    	view.addObject("webimport",UKDataContext.getWebIMPort()) ;
	    	view.addObject("sessionid", UKTools.getContextID(arg0.getSession().getId())) ;
			*//**
			 * WebIM共享用户
			 *//*
			User imUser = (User) arg0.getSession().getAttribute(UKDataContext.IM_USER_SESSION_NAME) ;
			if(imUser == null && view!=null){
				imUser = new User();
				imUser.setUsername(UKDataContext.GUEST_USER) ;
				imUser.setId(UKTools.getContextID(arg0.getSession(true).getId())) ;
				imUser.setSessionid(imUser.getId()) ;
				view.addObject("imuser", imUser) ;
			}
			if(!StringUtils.isBlank(arg0.getParameter("msg"))){
				view.addObject("msg", arg0.getParameter("msg")) ;
			}
			{//记录用户行为日志
				long start = (long) arg0.getAttribute("starttime") ;
				UserHistory userHistory = new UserHistory() ;
				userHistory.setTimes((int) ((System.currentTimeMillis() - start)));
				String url = arg0.getRequestURL().toString() ;
				if(url.length() >255){
					userHistory.setUrl(url.substring( 0 , 255));
				}else{
					userHistory.setUrl(url);
				}
				userHistory.setParam(UKTools.getParameter(arg0));
				if(menu!=null){
					userHistory.setMaintype(menu.type());
					userHistory.setSubtype(menu.subtype());
					userHistory.setName(menu.name());
					userHistory.setAdmin(menu.admin());
					userHistory.setAccess(menu.access());
				}
				if(user!=null){
					userHistory.setCreater(user.getId());
					userHistory.setUsername(user.getUsername());
					userHistory.setOrgi(user.getOrgi());
				}
				userHistory.setSessionid(arg0.getSession().getId());
				userHistory.setHostname(arg0.getRemoteHost());
				userHistory.setIp(arg0.getRemoteAddr());
				IP ipdata = IPTools.getInstance().findGeography(arg0.getRemoteAddr());
				userHistory.setCountry(ipdata.getCountry());
				userHistory.setProvince(ipdata.getProvince());
				userHistory.setCity(ipdata.getCity());
			    userHistory.setIsp(ipdata.getIsp());
			    
			    BrowserClient client = UKTools.parseClient(arg0) ;
			    userHistory.setOstype(client.getOs());
			    userHistory.setBrowser(client.getBrowser());
			    userHistory.setMobile(CheckMobile.check(arg0.getHeader("User-Agent")) ? "1" : "0");
				
				UKTools.published(userHistory);
			}
    	}*/
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