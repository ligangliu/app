package com.fanxun.common.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Set;

/**
 * @Author liu
 * @Date 2018-10-30 23:03
 */
public class JedisUtil {

    private static JedisPool jedisPool = null;

    private static String host = "39.108.102.177";

    private static Integer port = 6379;

    private static String password = "fanxun123";

    private static Integer timeout = 2000;

    static {
        if(jedisPool == null) {
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
            jedisPool = new JedisPool(config, host, port,  timeout, password);
        }
    }

//    public static void main(String[] args) {
//        Set<String> result = keys("*");
//        for (String r:result){
//            System.out.println(r);
//        }
//        System.out.println(get("a"));
//    }
    private static Jedis getJedis() {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
        }catch (JedisConnectionException e){
            System.out.println(e.getMessage());
            try{
                Thread.sleep(30000);
                jedis = jedisPool.getResource();
            }catch (Exception e2){
                if (e2 instanceof JedisConnectionException){
                    //尝试第二次连接也没有连接
                    //应该发送一个消息通知给管理员，通知其redis服务器可能宕机了
                }
                System.out.println(e2.getMessage());
            }
        }
        return jedis;
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
