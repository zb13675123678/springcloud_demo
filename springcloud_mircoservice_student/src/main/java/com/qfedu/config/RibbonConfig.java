package com.qfedu.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @auther: Zhangbo
 * @date: 2020/5/14 0:09
 * @Description:  ribbon+RestTemplate 负载均衡和远程调用模板的配置
 */
@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced   //开启负载均衡注解
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 切换负载均衡策略
     * @return  指定负载均衡的策略
     */
    @Bean
    public IRule getRule() {
        return new RandomRule();
    }


}
