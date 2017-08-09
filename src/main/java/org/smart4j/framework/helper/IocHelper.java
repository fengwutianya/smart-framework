package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.ClassUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by xuan on 2017/8/6 0006.
 */
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> entry: beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object  beanInstance = entry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {  //bean通过newinstance得到,无参构造,这里注入值
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);    //依赖注入
                            }
                        }
                    }
                }
            }
        }
    }
}
