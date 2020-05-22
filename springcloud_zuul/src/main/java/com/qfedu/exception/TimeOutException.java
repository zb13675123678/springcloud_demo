package com.qfedu.exception;

import com.netflix.zuul.exception.ZuulException;

/**
 * @auther: Zhangbo
 * @date: 2020/5/18 3:46
 * @Description:  自定义网关异常
 *
 *          如果继承ZuulException,可以复用Zuul的异常机制,并返回给控制页面
 */
public class TimeOutException extends ZuulException {


    /**
     * 继承的zuulException的代码(代码复用)
     *
     * @param throwable
     * @param nStatusCode
     * @param errorCause
     */
    public TimeOutException(Throwable throwable, int nStatusCode, String errorCause) {
        super(throwable, nStatusCode, errorCause);
    }

}
