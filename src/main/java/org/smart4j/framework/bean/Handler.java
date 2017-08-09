package org.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * Created by xuan on 2017/8/6 0006.
 */
public class Handler {
    /*
    request 对应的 controller类里面的Action方法
     */
    private Class<?> controllerClass;
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
