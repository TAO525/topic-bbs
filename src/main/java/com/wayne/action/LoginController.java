package com.wayne.action;

import com.alibaba.fastjson.JSONObject;
import com.wayne.common.HashUtil;
import com.wayne.common.WebUtils;
import com.wayne.model.BbsUser;
import com.wayne.service.BbsUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author TAO
 * @Date 2017/3/22 18:13
 */
@RestController
@RequestMapping("/bbs/user")
public class LoginController extends BaseController{
    @Resource
    private BbsUserService bbsUserService;


    @RequestMapping("/login.html")
    public ModelAndView loginHtml(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("index");
    }

    /**
     * 登录方法改为ajax方式登录
     * @param userName
     * @param password
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public JSONObject login(String userName, String password, HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        result.put("err", 1);
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
            result.put("msg","请输入正确的内容！");
        }else{
            password = HashUtil.generatePwd(password);
            BbsUser user = bbsUserService.getUserAccount(userName, password);
            if(user==null){
                result.put("msg","用户不存在");
            }else{
                WebUtils.loginUser(request, response, user, true);
                result.put("msg", "/bbs/index/1.html");
                result.put("err", 0);
            }
        }
        return result;
    }

}
