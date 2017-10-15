package com.wayne.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;


/**
 * @Author TAO
 * @Date 2017/3/24 14:25
 */
public class BaseModel implements Serializable {

    private static final transient long serialVersionUID = 1L;

    public BaseModel() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    public String toLineString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE).toString();
    }

}
