package com.qfedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @auther: Zhangbo
 * @date: 2020/5/19 3:41
 * @Description:
 */
@SpringBootApplication
@EnableConfigServer     //开启分布式配置中心服务
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class,args);
        System.out.println("分布式配置中心启动");
    }
}
