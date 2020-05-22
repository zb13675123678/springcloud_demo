package com.qfedu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: Zhangbo
 * @date: 2020/5/14 2:04
 * @Description:
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @RequestMapping("/queryNameByTid")
    public String queryNameByTid(@RequestParam("tid") Integer tid){
        switch (tid){
            case 1:
                //测试各种超时使用
                /*try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

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
