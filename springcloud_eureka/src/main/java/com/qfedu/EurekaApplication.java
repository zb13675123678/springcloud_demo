package com.qfedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @auther: Zhangbo
 * @date: 2020/5/13 10:17
 * @Description:
 */
@SpringBootApplication
@EnableEurekaServer //添加Eureka的服务端注解
public class EurekaApplication {

    public static void main(String[] args) {
        /**
         * 误区:
         *      run的第一个参数一定是添加了@SpringBootApplication注解类
         *      不是当前类
         */
        SpringApplication.run(EurekaApplication.class,args);
        System.out.println("Euraka注册中心启动了");
    }
}
