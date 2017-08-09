package org.smart4j.framework.bean;

import org.smart4j.framework.util.CastUtil;

import java.util.Map;

/**
 * HttpServletRequest中获取的组成对象Param
 * Created by xuan on 2017/8/6 0006.
 */
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public String getString(String name) {
        return CastUtil.castString(paramMap.get(name));
    }

    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(paramMap.get(name));
    }

    public int getInt(String name) {
        return CastUtil.castInt(paramMap.get(name));
    }

    public double getDouble(String name) {
        return CastUtil.castDouble(paramMap.get(name));
    }
}
