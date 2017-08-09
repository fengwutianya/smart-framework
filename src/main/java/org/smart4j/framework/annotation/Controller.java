package org.smart4j.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xuan on 2017/8/6 0006.
 */
@Target(ElementType.TYPE)               //class注解
@Retention(RetentionPolicy.RUNTIME)     //运行时
public @interface Controller {
}
