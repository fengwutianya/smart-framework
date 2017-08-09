package org.smart4j.framework.proxy;

/**
 * Created by xuan on 2017/8/8 0008.
 */
public interface Proxy {
    /**
     * 代理接口 链式代理操作
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
