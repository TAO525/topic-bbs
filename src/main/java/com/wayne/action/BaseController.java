package com.wayne.action;

import com.wayne.common.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author TAO
 * @Date 2017/3/27 14:54
 */
@Controller
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    //验证码的key
    protected static final String CODE_NAME = "verCode";

    @RequestMapping("/")
    public ModelAndView _index(HttpServletRequest request){
        logger.info(WebUtils.getIP(request)+"==首页访问");
        return new ModelAndView( "forward:/bbs/index/1.html");
    }

}
