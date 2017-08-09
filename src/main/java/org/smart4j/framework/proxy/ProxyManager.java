package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 输入目标类和一堆Proxy接口的实现类,生成代理对象
 * Created by xuan on 2017/8/8 0008.
 */
public class ProxyManager {
    @SuppressWarnings("unchecked")
    //扫描的时候能得到一大堆Proxy，往往是AspectProxy的子类，实现了模板方法中的许多钩子方法，begin(), end() before(), after()等
    //要将这些代理逻辑加到targetClass上面
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {   //the callback
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                开始链式执行过程 在这里启动
                return new ProxyChain(proxyList, targetClass, o, method, methodProxy, objects).doProxyChain();
            }
        });
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(targetClass);
//        enhancer.setCallback(new Interceptor(proxyList, targetClass));
//        return (T) enhancer.create();
    }
}

//class Interceptor implements MethodInterceptor {
//    List<Proxy> proxyList;
//    Class<?> targetClass;
//
//    public Interceptor(List<Proxy> proxyList, Class<?> targetClass) {
//        this.proxyList = proxyList;
//        this.targetClass = targetClass;
//    }
//
//    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//        相当于jdk动态代理的invokeHandler，返回的是result结果
//        而在定义的aop框架里面，ProxyChain最终才返回结果result，需要调用的是doProxyChain方法
//        return new ProxyChain(proxyList, targetClass, obj, method, proxy, args).doProxyChain();
//    }
//}
