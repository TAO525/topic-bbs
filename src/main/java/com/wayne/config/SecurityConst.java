package com.wayne.config;

/**
 * @Author TAO
 * @Date 2017/3/27 13:04
 */
public class SecurityConst {

    //安全级别1
    public static int LEVEL1_TIME = 1; //认定攻击阈值
    public static int LEVEL2_TIME = 5; //认定频繁发送阈值
    public static String LEVEL1_ = "LEVEL1_";
    public static String LEVEL2_ = "LEVEL2_";
    public static String HACKS_ = "HACKS_"; //黑名单
    public static String LIMITS_ = "LIMITS_";
    public static int LIMITS_COUNT = 1; //限制数量
}
