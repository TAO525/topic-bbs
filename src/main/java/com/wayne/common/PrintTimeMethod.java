package com.wayne.common;

import freemarker.template.SimpleDate;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * freemark 自定义函数 将等级转化为对应的名称
 * @Author TAO
 * @Date 2017/4/19 15:10
 */
public class PrintTimeMethod implements TemplateMethodModelEx {
    private final static SimpleDateFormat MY_DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");

    @Override
    public Object exec(List args) throws TemplateModelException {
        if(args.size() != 1){
            throw new TemplateModelException("Wrong arguments");
        }

        SimpleDate date = (SimpleDate) args.get(0);
        return getNiceDate(date.getAsDate());
    }


    private String getNiceDate(Date date){
        if (null == date) return "";
        String result = null;
        long currentTime = System.currentTimeMillis() - date.getTime();
        int time = (int)(currentTime / 1000);
        if(time < 60) {
            result = "刚刚";
        } else if(time >= 60 && time < 3600) {
            result = time/60 + "分钟前";
        } else if(time >= 3600 && time < 86400) {
            result = time/3600 + "小时前";
        } else if(time >= 86400 && time < 864000) {
            result = time/86400 + "天前";
        } else{
            result = MY_DATE_FORMAT.format(date);
        }
        return result;
    }
}


