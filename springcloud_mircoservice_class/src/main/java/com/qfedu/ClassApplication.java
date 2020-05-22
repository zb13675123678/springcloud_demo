package com.qfedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @auther: Zhangbo
 * @date: 2020/5/14 0:48
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient     //开启eureka注册中心
@EnableFeignClients     //开启fegin远程调用
public class ClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassApplication.class,args);
    }
}
