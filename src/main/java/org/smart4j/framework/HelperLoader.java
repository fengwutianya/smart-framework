package org.smart4j.framework;

import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.ClassUtil;

/**
 * Created by xuan on 2017/8/6 0006.
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
