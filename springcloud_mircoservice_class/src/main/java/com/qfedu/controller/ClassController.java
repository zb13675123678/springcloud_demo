package com.qfedu.controller;

import com.qfedu.feign.ITeacherFeginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: Zhangbo
 * @date: 2020/5/14 0:52
 * @Description:
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ITeacherFeginService teacherFegin;

    @RequestMapping("/queryNameByCid")
    public String queryNameByCid(Integer cid){

        switch (cid){
            case 1:
                String teacherInfo1 = teacherFegin.queryNameByTid(cid);
                return "一年级,班主任是:"+teacherInfo1;
            case 2:
                String teacherInfo2 = teacherFegin.queryNameByTid(cid);
                return "二年级,班主任是:"+teacherInfo2;
            case 3:
                String teacherInfo3 = teacherFegin.queryNameByTid(cid);
                return "三年级,班主任是:"+teacherInfo3;
            default:
                return "班级不存在";
        }
    }

}
