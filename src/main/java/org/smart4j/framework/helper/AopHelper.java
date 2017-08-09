package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by xuan on 2017/8/8 0008.
 */
public class AopHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry: targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);     //这里把所有targetClass都赋值proxy了，用proxy直接代替原来类的对象
            }
        } catch (Exception e) {
            LOGGER.error("aop failure", e);
        }
    }

    //带有@Aspect(Class<? extends Annotation>)注解，value也是注解，取出value的值作为注解，拿到后面这个注解的所有类
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();

        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }
    /**
     * 一个切面类 代理类 可以用在多个target上面，这也是aop的目的之一呀，获取这个一对多的关系Map<Class<?>,set<Class<?>>>
     */
    public static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }
    /**
     * 构造proxyList了，对于每个targetClass而言都有一个proxyList
     * 错误理解，这里每一个proxy都是对象了，而且这里proxy对象不是单例
     */
    public static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
