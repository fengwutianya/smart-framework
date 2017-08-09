package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xuan on 2017/8/6 0006.
 */
public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    //base package 下面所有的类
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    //主要是为了获取继承了abstract类AspectProxy 调用模板方法doProxy，致力提供一个通用的方法
    //即某父类或接口的所有子类
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAssignableFrom(superClass) || cls.equals(superClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    //获取含有某种注解的所有类的集合 比方说Aspect类
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    //Controller类
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class cls : CLASS_SET) {
            //如果被@Controller注解
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    //Service类
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    //所有bean 被容器管理注入的类
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }
}
