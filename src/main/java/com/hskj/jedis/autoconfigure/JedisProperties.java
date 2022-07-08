package com.hskj.jedis.autoconfigure;

import com.hskj.jedis.JedisConstant;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "jedis")
public class JedisProperties {
    /***************************** REDIS BASE PROPERTIES *****************************/
    /**
     * redis默认数据库
     */
    private int database = JedisConstant.DATA_BASE;
    /**
     * redis ip主机
     */
    private String host = JedisConstant.HOST;
    /**
     * 端口号
     */
    private int port  = JedisConstant.PORT ;
    /**
     * 连接超时配置（默认三秒）
     */
    private int connectionTimeoutMillis = JedisConstant.CONNECTION_TIMEOUT;
    /**
     * redis链接未占满等待得时间
     */
    private int socketTimeoutMillis = JedisConstant.FREE_TIMEOUT;
    /**
     * redis链接占满等待得时间
     */
    private int blockingSocketTimeoutMillis = JedisConstant.NO_FREE_TIMEOUT;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * redis-client名称
     */
    private String clientName = JedisConstant.CLIENT_NAME;
    /**
     * 是否使用ssl
     */
    private boolean ssl = JedisConstant.SSL;
    /***************************** REDIS BASE PROPERTIES *****************************/

    /***************************** POOL PROPERTIES CONFIG ******************************/
    private String poolType = JedisConstant.POOL_TYPE;
    /**
     * 池中最大活跃数（使用）
     */
    private int maxActive = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;
    /**
     * 池中最大空闲数
     */
    private int maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;
    /**
     * 池中最小空闲数
     */
    private int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;
    /**
     * 池中淘汰资源时间（..内）ms
     */
    private Duration durationBetweenRuns = Duration.ofMillis(-1);
    /***************************** POOL PROPERTIES CONFIG ******************************/

}
