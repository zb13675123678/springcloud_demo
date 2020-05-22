package com.qfedu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther: Zhangbo
 * @date: 2020/5/16 17:22
 * @Description:  pre过滤器实现的请求信息日志打印等等功能
 *          拓展:可以书写应用架构的浏览量代码等
 *
 * zuul过滤器,
 *    作用:可以用于身份认证,日志记录,服务异常处理,结果的处理
 *
 * 自定义过滤器的实现方式:
 *     1,自定义MyZuulFilter类 extends ZuulFilter
 *     2,当前类型的对象,必须交给Spring管理
 *     3,继承父类,实现4个中抽象方法
 *          filterType: 过滤器的类型(4种)
 *          filterOrder: 同类型过滤器的执行顺序
 *          shouldFilter: 当前过滤器是否生效
 *          run:   运行具体的过滤执行功能代码
 *                  1,可以获取url路径处理
 *                  2,可以结果进行处理
 */
@Component
public class LoggerZuulFilter extends ZuulFilter {

    /**
     * 过滤器的类型:可选值有
     *      pre  -  前置过滤
     *      route - 路由后过滤
     *      error - 异常过滤
     *      post  - 远程服务调用后过滤
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    //过滤器的执行顺序
    @Override
    public int filterOrder() {
        return 0;
    }

    //过滤器的开关
    @Override
    public boolean shouldFilter() {
        return false;
    }

    //过滤器的执行业务逻辑书写
    @Override
    public Object run() throws ZuulException {
        ////////逻辑代码一:日志功能的实现///////////////////
        //通过zuul内置对象,获取客户端请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = requestContext.getRequest();
        //获取日志对象
        Logger logger = LoggerFactory.getLogger(LoggerZuulFilter.class);
        //打印日志
        logger.info("LogFilter..日志打印方法和url.method={},url={}", request.getMethod(),request.getRequestURI().toString());
        ////////逻辑代码一:日志功能的实现///////////////////

        ////////逻辑代码二:请求url的cookie实现(不携带Cookie,不进行zuul路由)///////////////////
        /*//获取request的上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取request的对象
        HttpServletRequest request = requestContext.getRequest();
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            //设置不进行路由
            requestContext.setSendZuulResponse(false);
            //并返回状态码
            requestContext.setResponseStatusCode(401);
            //设置返回体
            requestContext.setResponseBody("no");
            //设置自定义键值对(可以用户前端业务判断)
            requestContext.set("isSuccess",false);
        }else{
            //设置进行路由
            requestContext.setSendZuulResponse(true);
            //并返回状态码
            requestContext.setResponseStatusCode(200);
            //设置自定义键值对(可以用户前端业务判断)
            requestContext.set("isSuccess",true);
        }*/
        ////////逻辑代码二:请求url的cookie实现///////////////////
        return null;
    }
}
