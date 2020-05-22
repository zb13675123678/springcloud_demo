package com.qfedu.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: Zhangbo
 * @date: 2020/5/14 16:26
 * @Description:  feign远程调用配置
 */
@Configuration
public class FeignConfig {

    /**
     * 切换负载均衡策略
     * @return
     */
    @Bean
    public IRule getRule(){
        return new RandomRule();
    }
}
