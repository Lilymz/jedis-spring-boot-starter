package com.hskj.jedis.autoconfigure;

import com.hskj.jedis.component.JedisInit;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration(
        proxyBeanMethods = false
)
@EnableConfigurationProperties(JedisProperties.class)
public class JedisAutoConfiguration {
    @Bean
    @ConditionalOnClass({GenericObjectPool.class, Jedis.class})
    public JedisInit jedisInit(){
        return new JedisInit();
    }
}
