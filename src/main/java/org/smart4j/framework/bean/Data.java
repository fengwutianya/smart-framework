package org.smart4j.framework.bean;

/**
 * JSON 数据封装 数据是一个map
 * Created by xuan on 2017/8/6 0006.
 */
public class Data {
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
