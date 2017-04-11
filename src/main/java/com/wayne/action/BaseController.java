package com.wayne.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author TAO
 * @Date 2017/3/27 14:54
 */
public class BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //验证码的key
    protected static final String CODE_NAME = "verCode";
}
