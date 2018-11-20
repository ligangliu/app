package com.fanxun.common.utils;

import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author liu
 * @Date 2018-10-31 12:03
 */
public class JedisSentinelUtil {
    private static JedisSentinelPool sentinelPool = null;

    private static String sentinel1_host = "172.18.72.73";

    private static Integer sentinel1_port = 26379;

    private static String sentinel2_host = "172.18.72.74";

    private static Integer sentinel2_port = 26379;

    private static String sentinel3_host = "172.18.72.75";

    private static Integer sentinel3_port = 26379;

    //当生产环境中，redis中的master与slave都应该配置成相同一个密码。
    private static String password = "fanxun123";

    private static Integer timeout = 2000;

    static {
        if(sentinelPool == null) {
            Set<String> sentinels = new HashSet<String>();
            sentinels.add(new HostAndPort(sentinel1_host, sentinel1_port).toString());
            sentinels.add(new HostAndPort(sentinel2_host, sentinel2_port).toString());
            sentinels.add(new HostAndPort(sentinel3_host, sentinel3_port).toString());
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(1000);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(100);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；单位毫秒
            //小于零:阻塞不确定的时间,  默认-1
            config.setMaxWaitMillis(1000*100);
            //在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            //return 一个jedis实例给pool时，是否检查连接可用性（ping()）
            config.setTestOnReturn(true);
            //connectionTimeout 连接超时（默认2000ms）
            //soTimeout 响应超时（默认2000ms）
            sentinelPool = new JedisSentinelPool("mymaster", sentinels, config, password);
        }

    }

    /**
     * 连接master主从复制的哨兵模式的master。
     * 当master挂了，会尝试连接两次，每次间隔30s
     * @return
     */
    private static Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = sentinelPool.getResource();
        }catch (Exception e){
            System.out.println(e.getMessage());
            try {
                Thread.sleep(30000);
                try {
                    jedis = sentinelPool.getResource();
                }catch (JedisConnectionException e1){
                    //应该此处有一个通知机制，通知管理员，redis的主从复制的master宕机了
                    System.out.println(e1.getMessage());
                    Thread.sleep(30000);
                    jedis = sentinelPool.getResource();
                }
            }catch (Exception e3){
                if (e3 instanceof JedisConnectionException){
                    //应该此处有一个通知机制，通知管理员，redis的主从复制不能正常切换
                }
                System.out.println(e3.getMessage());
                //e3.printStackTrace();
            }
        }
        return jedis;
    }

    public static void main(String[] args) {
        System.out.println("Current master: " + sentinelPool.getCurrentHostMaster().toString());
        Set<String> keys = keys("*");
        for (String key:keys){
            System.out.println(key);
        }
        System.out.println(JedisSentinelUtil.set("d","1"));
    }

    //*************************************************************
    //redis对于基本操作
    //*************************************************************
    //对于value为数字的key自动加1
    public static long incr(String key) {
        Jedis jedis = getJedis();
        long result = jedis.incr(key);
        jedis.close();
        return result;
    }
    public static long incrby(String key,long number){
        Jedis jedis = getJedis();
        long result = jedis.incrBy(key,number);
        jedis.close();
        return result;
    }
    public static long decr(String key) {
        Jedis jedis = getJedis();
        long result = jedis.decr(key);
        jedis.close();
        return result;
    }
    public static long decrby(String key,long number){
        Jedis jedis = getJedis();
        long result = jedis.decrBy(key,number);
        jedis.close();
        return result;
    }
    public static long expire(String key, int seconds) {
        Jedis jedis = getJedis();
        long result = jedis.expire(key, seconds);
        jedis.close();
        return result;
    }
    public static long ttl(String key) {
        Jedis jedis = getJedis();
        long result = jedis.ttl(key);
        jedis.close();
        return result;
    }
    //删除某个key
    public static long del(String key) {
        Jedis jedis = getJedis();
        long result = jedis.del(key);
        jedis.close();
        return result;
    }
    //判断某个key是否存在
    public static boolean exists(String key){
        Jedis jedis = getJedis();
        boolean result = jedis.exists(key);
        jedis.close();
        return result;
    }
    public static Set<String> keys(String pattern){
        Jedis jedis = getJedis();
        Set<String> result = jedis.keys(pattern);
        jedis.close();
        return result;
    }
    //*************************************************************
    //redis对于string的操作
    //*************************************************************

    public static String get(String key) {
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }
    public static String set(String key, String value) {
        Jedis jedis = getJedis();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }

    /**
     * 当key不存在才起作用，当key存在的时候返回0(在做分布式锁的时候特别有用)
     * @param key
     * @param value
     * @return
     */
    public static long setnx(String key,String value){
        Jedis jedis = getJedis();
        long result = jedis.setnx(key,value);
        jedis.close();
        return result;
    }
    //返回key对应value的值
    public static long strlen(String key){
        Jedis jedis = getJedis();
        long result = jedis.strlen(key);
        jedis.close();
        return result;
    }
    public static long append(String key,String value){
        Jedis jedis = getJedis();
        long result = jedis.append(key,value);
        jedis.close();
        return result;
    }
    //*************************************************************
    //redis对于list的操作
    //*************************************************************
    public static long lpush(String key,String[] values){
        Jedis jedis = getJedis();
        long result = jedis.lpush(key,values);
        jedis.close();
        return result;
    }
    public static long rpush(String key,String[] values){
        Jedis jedis = getJedis();
        long result = jedis.rpush(key,values);
        jedis.close();
        return result;
    }
    public static String lpop(String key){
        Jedis jedis = getJedis();
        String result = jedis.lpop(key);
        jedis.close();
        return result;
    }
    public static String rpop(String key){
        Jedis jedis = getJedis();
        String result = jedis.rpop(key);
        jedis.close();
        return result;
    }
    public static long llen(String key){
        Jedis jedis = getJedis();
        long result = jedis.llen(key);
        jedis.close();
        return result;
    }
    public static String lindex(String key, long index){
        Jedis jedis = getJedis();
        String result = jedis.lindex(key,index);
        jedis.close();
        return result;
    }
    public static String lset(String key,long index,String value){
        Jedis jedis = getJedis();
        String result = jedis.lset(key,index,value);
        jedis.close();
        return result;
    }

    //遍历list
    public static List<String> lrange(String key){
        Jedis jedis = getJedis();
        List<String> result = jedis.lrange(key,0,-1);
        jedis.close();
        return result;
    }
    public static List<String> lrange(String key,long start,long end){
        Jedis jedis = getJedis();
        List<String> result = jedis.lrange(key,start,end);
        jedis.close();
        return result;
    }
    //只保留list中某一部分元素
    public static String ltrim(String key,long start,long end){
        Jedis jedis = getJedis();
        String result = jedis.ltrim(key,start,end);
        jedis.close();
        return result;
    }
    //*************************************************************
    //redis对于hash的操作
    //*************************************************************

    public static String hget(String hkey, String key) {
        Jedis jedis = getJedis();
        String result = jedis.hget(hkey, key);
        jedis.close();
        return result;
    }
    public static long hset(String hkey, String key, String value) {
        Jedis jedis = getJedis();
        long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }
    public static long hdel(String hkey, String key) {
        Jedis jedis = getJedis();
        long result = jedis.hdel(hkey, key);
        jedis.close();
        return result;
    }

    //*************************************************************
    //redis对于set的操作
    //*************************************************************
    public static long sadd(String key, String value) {
        Jedis jedis = getJedis();
        long result = jedis.sadd(key,value);
        jedis.close();
        return result;
    }
    public static long sadd(String key, List<String> values) {
        Jedis jedis = getJedis();
        long result = jedis.sadd(key,(String[]) values.toArray());
        jedis.close();
        return result;
    }
    public static long srem(String key, String value) {
        Jedis jedis = getJedis();
        long result = jedis.srem(key,value);
        jedis.close();
        return result;
    }
    public static long srem(String key, List<String> values) {
        Jedis jedis = getJedis();
        long result = jedis.srem(key,(String[]) values.toArray());
        jedis.close();
        return result;
    }
    public static Set<String> smembers(String key) {
        Jedis jedis = getJedis();
        Set<String> result = jedis.smembers(key);
        jedis.close();
        return result;
    }
    public static long sismenber(String key, String value) {
        Jedis jedis = getJedis();
        boolean result = jedis.sismember(key,value);
        jedis.close();
        if (result){
            return 1;
        }else {
            return 0;
        }
    }
    public static long ssize(String key) {
        Jedis jedis = getJedis();
        long result = jedis.scard(key);
        jedis.close();
        return result;
    }
}
