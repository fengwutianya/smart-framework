package org.smart4j.framework.helper;

import org.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xuan on 2017/8/6 0006.
 */
public class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    //类加载器
    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : beanClassSet) {
            BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls)); //这样的话是单例模式？
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }
}
