package org.smart4j.framework.annotation;

/**
 * Created by xuan on 2017/8/8 0008.
 */

import java.lang.annotation.*;

/**
 * 切面注解
 */
@Target(ElementType.TYPE)   //类
@Retention(RetentionPolicy.RUNTIME) //保留到运行时
public @interface Aspect {
    Class<? extends Annotation> value();    //注解类,比方说Inject.class
}
