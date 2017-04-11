package com.wayne.action;

import com.alibaba.fastjson.JSONObject;
import com.wayne.common.HashUtil;
import com.wayne.common.WebUtils;
import com.wayne.model.BbsUser;
import com.wayne.service.BbsUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        return new ModelAndView("signin");
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


    @RequestMapping("/regist.html")
    public ModelAndView registHtml(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("regist");
    }

    /**
     * 注册改为 ajax 方式注册
     * @param user
     * @param code
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @PostMapping("/doRegister")
    public JSONObject  register(BbsUser user,String code,HttpServletRequest request,HttpServletResponse response){
        JSONObject result  = new JSONObject();
        result.put("err", 1);
        HttpSession session = request.getSession(true);
        String verCode = (String)session.getAttribute(CODE_NAME);
        if(!verCode.equalsIgnoreCase(code)){
            result.put("msg", "验证码输入错误");
        }else if(bbsUserService.hasUser(user.getUserName())){
            result.put("msg", "用户已经存在");
        }else{
            String password = HashUtil.generatePwd(user.getPassword());
            user.setPassword(password);
            user.setBalance(10);
            user.setLevel(1);
            user.setScore(10);
            user = bbsUserService.setUserAccount(user);
            WebUtils.loginUser(request, response, user, true);
            result.put("err", 0);
            result.put("msg", "/bbs/index");
        }
        return result;
    }

}
