package com.hskj.jedis.component;

import com.hskj.jedis.JedisConstant;
import com.hskj.jedis.JedisUtil;
import com.hskj.jedis.autoconfigure.JedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.*;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * spring-boot jedis初始化
 * @author zhangjie
 */
@Slf4j
public class JedisInit{
    @Autowired
    private JedisProperties jedisProperties;
    private ThreadLocal<JedisPool> pool ;
    private ThreadLocal<JedisSentinelPool> setinelPool;
    private ThreadLocal<JedisCluster>  cluster ;
    @PostConstruct
    public void init(){
        initPool();
    }
    /**
     * 初始化连接池
     */
    protected void initPool(){
        // 初始化threadLocal
        pool = new ThreadLocal<JedisPool>();
        setinelPool = new ThreadLocal<JedisSentinelPool>();
        cluster = new ThreadLocal<JedisCluster>();
        log.debug("jedis pool init ...");
        this.setPool();
        log.debug("jedis pool init complete，当前redis模式:{}", JedisUtil.mode(jedisProperties.getPoolType()));
    }

    /**
     * 池初始化（只会走其中一个初始化）
     */
    protected void setPool(){
        // 单机版
        aloneInit();
        // 集群
        clusterInit();
        // 哨兵
        setinelInit();
    }

    private void setinelInit() {
    }

    private void clusterInit() {
    }
    public void brokenClose(Jedis jedis){
       try {
           if (Objects.nonNull(jedis) &&
                   JedisConstant.TYPE.ALONE.equalsIgnoreCase(jedisProperties.getPoolType())){
               pool.get().returnBrokenResource(jedis);
           }
           if (Objects.nonNull(jedis) &&
                   JedisConstant.TYPE.SENTINEL.equalsIgnoreCase(jedisProperties.getPoolType())){
               setinelPool.get().returnBrokenResource(jedis);
           }
       }catch (Exception e){
           log.error("关闭redis错误,原因:{}",e.getMessage());
       }
    }
    public void close(Jedis jedis){
        try {
            if (Objects.nonNull(jedis) &&
                    JedisConstant.TYPE.ALONE.equalsIgnoreCase(jedisProperties.getPoolType())){
                pool.get().returnResource(jedis);
            }
            if (Objects.nonNull(jedis) &&
                    JedisConstant.TYPE.SENTINEL.equalsIgnoreCase(jedisProperties.getPoolType())){
                setinelPool.get().returnResource(jedis);
            }
        }catch (Exception e){
            log.error("关闭redis错误,原因:{}",e.getMessage());
        }
    }
    public Jedis getJedis(){
        try {
            if (JedisConstant.TYPE.ALONE.equalsIgnoreCase(jedisProperties.getPoolType())){
                return pool.get().getResource();
            }
            else if (JedisConstant.TYPE.SENTINEL.equalsIgnoreCase(jedisProperties.getPoolType())){
                return setinelPool.get().getResource();
            }
        }catch (Exception e){
            log.error("获取redis错误,原因:{}",e.getMessage());
        }
        return null;
    }
    public JedisCluster getCluster(){
        if (!JedisConstant.TYPE.CLUSTER.equalsIgnoreCase(jedisProperties.getPoolType())){
            log.warn("当前redis模式非集群模式，不能获取jedisCluster");
            return null;
        }
        return cluster.get();
    }

   /**
    * 单机redis初始化
    * @Author zhangjie
    * @Date: 2022/7/8 15:42
    */
    public void aloneInit(){
        //单机版本
        if (JedisConstant.TYPE.ALONE.equalsIgnoreCase(jedisProperties.getPoolType())){
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(jedisProperties.getMaxActive());
            config.setMaxIdle(jedisProperties.getMaxIdle());
            config.setMinIdle(jedisProperties.getMinIdle());
            JedisPool jedisPool;
            try {
                // 是否使用ssl登录(使用ssl得方式进行初始化)
                if (jedisProperties.isSsl()){
                    jedisPool =new JedisPool(config,jedisProperties.getHost(),jedisProperties.getPort(),
                            jedisProperties.getConnectionTimeoutMillis(),jedisProperties.getSocketTimeoutMillis(),
                            jedisProperties.getPassword(),jedisProperties.getDatabase(),jedisProperties.getClientName(),
                            jedisProperties.isSsl());
                }
                // 账号密码登录
                else {
                    jedisPool = new JedisPool(config,jedisProperties.getHost(), jedisProperties.getPort(),
                            jedisProperties.getConnectionTimeoutMillis(),jedisProperties.getSocketTimeoutMillis(),
                            jedisProperties.getBlockingSocketTimeoutMillis(),jedisProperties.getUsername(),
                            jedisProperties.getPassword(),jedisProperties.getDatabase(),jedisProperties.getClientName()
                    );
                }
                pool.set(jedisPool);
            }catch (Exception e){
                log.error("init pool error,reason：{}",e);
            }
        }
    }
}
