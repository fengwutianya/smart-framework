package org.smart4j.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * jsp 封装
 * Created by xuan on 2017/8/6 0006.
 */
public class View {
    //jsp的位置 和里面要填充的参数
    private String path;
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        this.model.put(key, value);
        return this;    //链式调用
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
