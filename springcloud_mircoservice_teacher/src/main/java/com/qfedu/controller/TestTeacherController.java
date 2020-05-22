package com.qfedu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: Zhangbo
 * @date: 2020/5/16 13:20
 * @Description: 测试zuul路由网关保护敏感路径使用
 */
@RestController
@RequestMapping("/teacher")
public class TestTeacherController {
    @RequestMapping("/test/queryNameByTid")
    public String queryNameByTid(@RequestParam("tid") Integer tid){
        switch (tid){
            case 1:
                return "张老师";
            case 2:
                return "李老师";
            case 3:
                return "王老师";
            default:
                return "此老师不存在";
        }
    }

}
