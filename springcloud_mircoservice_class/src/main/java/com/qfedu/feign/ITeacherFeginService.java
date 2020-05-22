package com.qfedu.feign;

import com.qfedu.feign.fallback.TeacherHystrixService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther: Zhangbo
 * @date: 2020/5/14 1:48
 * @Description:
 */
@FeignClient(value = "MIRCO-TEACHER",fallback = TeacherHystrixService.class)   //远程调用的微服务(不用实现类,底层有动态代理技术)
public interface ITeacherFeginService {

    /**
     * fegin调用远程微服务的本地接口:
     *  原理:会根据本地方法接口与微服务接口动态代理实现
     *  1:方法名,参数,api,请求方式,必须相同
     *  2,方法参数:必须使用RequestParam
     */

    /**
     *根据教师id,查询教师信息
     * @param tid  教师id
     * @return   返回教师信息
     */
    @RequestMapping("/teacher/queryNameByTid")
    public String queryNameByTid(@RequestParam("tid") Integer tid);



}
