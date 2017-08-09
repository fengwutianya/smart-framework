package com.xuan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * Created by xuan on 2017/8/9 0009.
 */
@Aspect(Controller.class)                           //给所有的Controller增加before和after的功能
public class ControllerAspect extends AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;
    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        LOGGER.debug("------------------------- begin ----------------------");
        LOGGER.debug(String.format("class: %s", cls.getName()));
        LOGGER.debug(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    protected void after(Class<?> cls, Method method, Object[] params) {
        LOGGER.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
        LOGGER.debug("-------------------------- end ----------------------");
    }
}
