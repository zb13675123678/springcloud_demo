package com.qfedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @auther: Zhangbo
 * @date: 2020/5/14 1:33
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient  //SpringCloud 2.x之后,该注解可以省略
public class TeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class,args);
    }
}
