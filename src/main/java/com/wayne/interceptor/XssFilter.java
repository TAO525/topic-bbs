package com.wayne.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**

 *

 * 使用注解标注过滤器

 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器

 * 属性filterName声明过滤器的名称,可选

 * 属性urlPatterns指定要过滤的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)

 */

@WebFilter(filterName="xssFilter",urlPatterns="/bbs/user/*")
public class XssFilter implements Filter{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig config) throws ServletException {

        logger.info("过滤器初始化");

    }




    @Override
    public void doFilter(ServletRequest request, ServletResponse response,

                         FilterChain chain) throws IOException, ServletException {

        chain.doFilter(new XssHttpServletRequestWrapper(
                (HttpServletRequest) request), response);

    }


    @Override
    public void destroy() {

        logger.info("过滤器销毁");

    }

}
