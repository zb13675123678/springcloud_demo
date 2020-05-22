package com.qfedu.feign.fallback;

import com.qfedu.feign.ITeacherFeginService;
import org.springframework.stereotype.Component;

/**
 * @auther: Zhangbo
 * @date: 2020/5/14 21:00
 * @Description:  本微服务的熔断降级
 */
@Component
public class TeacherHystrixService implements ITeacherFeginService {

    @Override
    public String queryNameByTid(Integer tid) {
        return "班级服务异常,服务降级";
    }
}
