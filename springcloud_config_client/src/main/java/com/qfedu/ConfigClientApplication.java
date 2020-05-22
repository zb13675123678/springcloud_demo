package com.qfedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther: Zhangbo
 * @date: 2020/5/21 3:55
 * @Description:
 */
@SpringBootApplication
public class ConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class,args);
        System.out.println("统一配置中心客户端demo启动了");
    }
}
