package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuan on 2017/8/8 0008.
 */
public class ProxyChain {
    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<Proxy>();
    private int proxyIndex = 0;

    public ProxyChain(List<Proxy> proxyList, Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams) {
        this.proxyList = proxyList;
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object methodResult = null;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);   //执行代理链中的方法doProxy,doProxy又会调用ProxyChain
                                                                        //的doProxyChain
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams); //运用cglib调用本来的方法
        }
        return methodResult;
    }
}
