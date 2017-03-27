package com.wayne.common;

import static com.wayne.config.LevelConst.*;

/**
 * @Author TAO
 * @Date 2017/3/27 10:53
 */
public class ScoreUtil {
    /**
     * 分5个级别
     *
     * @param score
     * @return
     */
    public static int getLevel(int score) {
        if (score >= PRESIDENT_THRESHOLD) {
            return 5;
        } else if (score >= DIRECTOR_THRESHOLD) {
            return 4;
        } else if (score >= TEACHER_THRESHOLD) {
            return 3;
        }
        if (score >= OLD_THRESHOLD) {
            return 2;
        } else {
            return 1;
        }
    }
}
