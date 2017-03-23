package com.wayne.action;

import com.wayne.service.BbsUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author TAO
 * @Date 2017/3/22 18:13
 */
@RestController
public class HelloController {
    @Resource
    private BbsUserService bbsUserService;

    @RequestMapping("/hello")
    public String say(){
        return "hello SpringBoot";
    }

    @RequestMapping(value = "/byname", method = RequestMethod.GET)
    @ResponseBody
    public void test_getUser(HttpServletRequest request) {

        ResponseEntity result = new ResponseEntity<>(bbsUserService.getUserByName("xxx"), HttpStatus.OK);
        System.out.println(result);
    }
}
