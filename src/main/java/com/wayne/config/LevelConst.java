package com.wayne.config;

/**
 * @Author TAO
 * @Date 2017/3/27 10:54
 */
public class LevelConst {

    private LevelConst(){}

    //level 用户等级
    public static final int REFRESH_THRESHOLD = 30;
    public static final int OLD_THRESHOLD = 100;
    public static final int TEACHER_THRESHOLD = 200;
    public static final int DIRECTOR_THRESHOLD = 350;
    public static final int PRESIDENT_THRESHOLD = 700;

    //积分类型
    public static final int BBS_TOPIC_SCORE = 10;
    public static final int BBS_POST_SCORE = 3;
    public static final int BBS_REPLAY_SCORE = 3;

    //获取等级名称
    public static String getLevelName(String param) {
        Integer level = Integer.valueOf(param);
        switch (level) {
            case 1:
                return "幼儿宝宝";
            case 2:
                return "小学生";
            case 3:
                return "中学生";
            case 4:
                return "大学生";
            default:
                return "导师";
        }
    }

}
