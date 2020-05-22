package com.qfedu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @auther: Zhangbo
 * @date: 2020/5/17 15:24
 * @Description:       error过滤器实现:最终异常处理(最后一道系统防线)
 * 根据zuul的erro过滤器,实现错误异常捕获及其日志打印(利用了zuul的过滤器特性)
 *          作用:给用户友好的异常信息反馈
 *
 *  知识点:
 *      从ZuulServletFilter源码中看到(自习看try{}和catch{}的postroute()),error可以在所有阶段捕获异常后执行，但是如果post阶段中出现异常被error处理后则不再回到post阶段执行，
 *      也就是说需要保证在post阶段不要有异常，因为一旦有异常后就会造成该过滤器后面其它post过滤器将不再被执行
 *
 */
@Component
public class ErrorExceptionFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    //测试完:已经关闭
    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("错误异常捕捉");
        //通过zuul内置对象,获取客户端请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取throwable对象  : throwable不为空,才会执行error过滤器代码
        Throwable throwable = requestContext.getThrowable();

        //获取日志对象
        Logger logger = LoggerFactory.getLogger(ErrorExceptionFilter.class);
        //打印日志信息 :如[ZuulErrorFilter] error message :com.netflix.zuul.exception.ZuulException: 429 TOO_MANY_REQUESTS
        logger.info("zuul异常过滤器的异常信息:[ZuulErrorFilter] error message: {}",throwable.getCause().getMessage());

        //返回客户端出错信息
        requestContext.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        requestContext.set("error.exception", throwable.getCause());

        return null;
    }
}
