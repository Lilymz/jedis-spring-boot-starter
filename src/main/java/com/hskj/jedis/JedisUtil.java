package com.hskj.jedis;

public class JedisUtil {
    /**
     * 获取redis模式
     *
     * @Author zhangjie
     * @Date: 2022/7/8 15:45
     * @param : poolType
     * @Return: java.lang.String
     */
    public static String mode(String poolType){
        if (JedisConstant.TYPE.ALONE.equalsIgnoreCase(poolType)){
            return "单机模式";
        }
        if (JedisConstant.TYPE.CLUSTER.equalsIgnoreCase(poolType)){
            return "集群模式";
        }
        if (JedisConstant.TYPE.SENTINEL.equalsIgnoreCase(poolType)){
            return "哨兵模式";
        }
        return "error模式";
    }
}
