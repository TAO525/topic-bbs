package com.wayne.common;

import com.wayne.config.LevelConst;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * freemark 自定义函数 将等级转化为对应的名称
 * @Author TAO
 * @Date 2017/4/19 15:10
 */
public class LevelMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List args) throws TemplateModelException {
        if(args.size() != 1){
            throw new TemplateModelException("Wrong arguments");
        }
        return LevelConst.getLevelName(String.valueOf(args.get(0)));
    }
}
