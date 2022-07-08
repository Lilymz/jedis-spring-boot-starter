package com.hskj.jedis;

/**
 * Jedis配置相关常量
 * @author zhangjie
 */
public class JedisConstant {
    /**
     * 连接类型
     */
    public interface TYPE {
        String ALONE ="ALONE";
        String CLUSTER = "CLUSTER";
        String SENTINEL = "SENTINEL";
    }
    /**
     * 内部会进行匹配
     * 连接池类型默认时单机模式：
     * 单机<p>alone</p>
     * 集群<p>cluster</p>
     * 哨兵<p>sentinel</p>
     */
    public static final String POOL_TYPE = "ALONE";
    /**
     * redis默认数据库
     */
    public static final int DATA_BASE = 0;
    /**
     * redis链接占满等待得时间
     */
    public static final int NO_FREE_TIMEOUT = 3000;
    /**
     * redis链接未占满等待得时间
     */
    public static final int FREE_TIMEOUT = 3000;
    /**
     * 连接超时时间
     */
    public static final int CONNECTION_TIMEOUT=3000;
    /**
     * 客户端名前缀
     */
    public static final String BASE_NAME = "REDIS-XM";
    /**
     *  客户端名称：redis+地区+主/从+编号
     */
    public static final String CLIENT_NAME = "REDIS-XM-M-001";
    /**
     * 是否建立ssl连接
     */
    public static final boolean SSL = false;
    /**
     * 主机
     */
    public static final String HOST = "127.0.0.1";
    /**
     * 端口号
     */
    public static final int PORT = 6379;

}
