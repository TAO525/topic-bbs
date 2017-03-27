package com.wayne.action;

import com.wayne.service.BbsUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @Author TAO
 * @Date 2017/3/22 18:13
 */
@RestController
public class HelloController {
    @Resource
    private BbsUserService bbsUserService;

    @RequestMapping("/hello")
    public ModelAndView say(){
        System.out.println(bbsUserService) ;

        return new ModelAndView("hello");
    }

}
