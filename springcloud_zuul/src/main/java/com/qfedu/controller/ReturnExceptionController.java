package com.qfedu.controller;

import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @auther: Zhangbo
 * @date: 2020/5/17 22:57
 * @Description:   系统异常页面返回controller
 */
@RestController
public class ReturnExceptionController implements ErrorController {

    /**
     * 跳转的错误页面
     */
    @Override
    public String getErrorPath() {

        return "/error";
    }


    /**
     * zuul的error异常处理
     *
     * @param request  Http请求
     * @param response API统一响应
     * @return
     */
    @RequestMapping("/error")
    public HashMap<Integer, String> error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取异常状态码
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_cod");
        //获取异常类型
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        String message = "";

        //判断当前对象是否是当前类的实例
        if (exception instanceof ZuulException) {
            //获取当前异常请求头的信息
            message = exception.getCause().getMessage();
        }

        //返回异常状态码:异常捕获的状态码
        response.setStatus(HttpStatus.OK.value());

        System.out.println("网关最终异常捕捉:返回给页面:服务系统维修,异常");

        //返回响应体数据:(不标准)
        //组装返回页面的信息
        HashMap<Integer, String> map = new HashMap<>();
        map.put(code,message);

        return map;
    }


}
