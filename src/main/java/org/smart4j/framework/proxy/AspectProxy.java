package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面类 调用ProxyManager createProxy 需要目标对类 和 一大堆代理对象 生成ProxyChain, 调用doProxyChain
 * doProxyChain 调用Proxy的doProxy执行横切逻辑,执行完再去执行ProxyChain的doProxyChain的方法,知道所有的
 * Proxy都执行完,再执行核心逻辑,methodProxy的invokeSuper方法
 * Created by xuan on 2017/8/8 0008.
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        //todo
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        //切面的执行逻辑框架 模板方法
        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);        //顺序
                result = proxyChain.doProxyChain(); //回去
                after(cls, method, params);         //逆序
            }else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(cls, method, params);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    //下面是一大堆钩子方法, 子类选择性实现

    protected void end() {

    }

    protected void error(Class<?> cls, Method method, Object[] params) {

    }

    protected void after(Class<?> cls, Method method, Object[] params) {

    }

    protected void before(Class<?> cls, Method method, Object[] params) {

    }

    protected boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    protected void begin() {
    }
}
