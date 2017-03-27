package com.wayne.interceptor;

import java.lang.annotation.*;

/**
 * 权限注解 可用在类和方法上 加上则需要用户登录
 * @Author TAO
 * @Date 2017/3/27 17:59
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}