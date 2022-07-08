package com.hskj.jedis.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class RedisCluster {
    @Autowired
    private JedisInit jedisInit;

}
