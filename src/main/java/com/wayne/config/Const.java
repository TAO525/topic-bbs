package com.wayne.config;

/**
 * @Author TAO
 * @Date 2017/3/27 13:04
 */
public class Const {

    //session相关
    public static String USER_SESSION_KEY = "user";

    // cookie认证相关
    public static String USER_COOKIE_KEY    = "uid";
    public static String USER_COOKIE_SECRET = "bbs&#%!&*";

    // page size
    public static int TOPIC_PAGE_SIZE = 16;     // 首页帖子分页大小
    public static int POST_PAGE_SIZE = 8;       // 跟帖分页大小
    public static int REPLY_PAGE_SIZE = 5;      // 帖子回复分页大小
    public static int PAGE_SIZE_FOR_ADMIN = 30; // 管理员后台（查看帖子，回帖，跟帖）的分页大小
}
