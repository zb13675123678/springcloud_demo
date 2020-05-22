package com.qfedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @auther: Zhangbo
 * @date: 2020/5/13 19:13
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient  //SpringCloud 2.x之后,该注解可以省略
@EnableHystrix  //开启Hystrix熔断器
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class,args);
    }

}
