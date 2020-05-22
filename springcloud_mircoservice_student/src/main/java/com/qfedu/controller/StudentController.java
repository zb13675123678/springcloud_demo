package com.qfedu.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @auther: Zhangbo
 * @date: 2020/5/13 22:16
 * @Description: 使用ribbon+RestTemplate实现远程调用
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private RestTemplate restTemplate;

    /*
    * 知识点1:
    *   @ResponseBody -> 返回json(错误);
    *   @ResponseBody -> 将方法的返回值,放入response响应体中;
    *
    * 响应体 -> 数值,字符串(json),二进制流.... ->进行处理后返回的json
    *
    * 知识点2:
    * Hystrix的资源隔离和服务降级:
    *     当前微服务出现问题时(线程堵塞,报错等),避免当前微服务被其他微服务调用造成雪崩,进行资源隔离.并进行服务降级,执行降级方法;
    * */
    /**
     * 根据学生id查询学生姓名
     * @param sid  学生id
     * @return  返回学生信息
     */
    @RequestMapping("/queryNameBySid")
    @HystrixCommand(fallbackMethod = "myFallBack")
    public String queryNameBySid(Integer sid){
        switch (sid){
            case 1:
                String classInfo1 = restTemplate.getForObject("http://MIRCO-CLASS/class/queryNameByCid?cid=1", String.class);
                return "小明,班级是:"+classInfo1;
            case 2:
                String classInfo2 = restTemplate.getForObject("http://MIRCO-CLASS/class/queryNameByCid?cid=2", String.class);
                return "小红,班级是:"+classInfo2;
            case 3:
                String classInfo3 = restTemplate.getForObject("http://MIRCO-CLASS/class/queryNameByCid?cid=3", String.class);
                return "小刚,班级是:"+classInfo3;
            default:
                return "查无此人";
        }
    }

    //服务降级方法
    public String myFallBack(Integer sid){

        return "学生服务异常,服务降级";
    }

}
