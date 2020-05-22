package com.qfedu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.qfedu.exception.TimeOutException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import java.time.LocalTime;

/**
 * @auther: Zhangbo
 * @date: 2020/5/18 3:42
 * @Description:  pre过滤器实现:0点到20点之间不可以访问系统,并自定义抛出异常
 */
public class TimeOutZuulFillter extends ZuulFilter {

    /**
     * 当前时间
     */
    private static final LocalTime NOW = LocalTime.now();

    /**
     * 零点
     */
    private static final LocalTime ZERO_CLOCK = LocalTime.of(0, 0);

    /**
     * 二十点
     */
    private static final LocalTime TWENTY_CLOCK = LocalTime.of(20, 0);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 5;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        //获取request的上下文:这里主要响应体
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取throwable对象  : throwable不为空,才会执行error过滤器代码
        Throwable throwable = requestContext.getThrowable();

        //如果在o~22点访问,就抛自定义异常
        if (NOW.isAfter(ZERO_CLOCK) && NOW.isBefore(TWENTY_CLOCK)) {
            //设置不进行路由
            requestContext.setSendZuulResponse(false);
            //并返回状态码
            requestContext.setResponseStatusCode(401);
            //设置返回体
            requestContext.setResponseBody("no");
            //设置自定义键值对(可以用户前端业务判断)
            requestContext.set("isSuccess",false);

            // 如果用户在0-20点之间访问了系统，则抛出异常
            throw new TimeOutException(throwable,401,"0~22点不允许访问");
        }else {

            System.out.println("比如放行,让别人使用游戏系统");
            ///////编写其他业务:比如放行,让别人使用游戏系统//////////////////
        }

        return null;
    }
}
