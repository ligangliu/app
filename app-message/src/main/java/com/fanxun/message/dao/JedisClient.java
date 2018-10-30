package com.fanxun.message.dao;

import java.util.List;
import java.util.Set;

public interface JedisClient {

	//对字符串的操作
	public String get(String key);
	public String set(String key, String value);
	public String hget(String hkey, String key);
	public long hset(String hkey, String key, String value);
	public long incr(String key);
	public long expire(String key, int seconds);
	public long ttl(String key);
	public long del(String key);
	public long hdel(String hkey, String key);

	//对集合得操作
	public long sadd(String key, String value);
	public long sadd(String key, List<String> values);
	public long srem(String key, String value);
	public long srem(String key, List<String> values);
	//遍历集合中元素
	public Set<String> smembers(String key);
	//判断集合中是否存在某个元素
	public long sismenber(String ket, String value);
	//获取集合中元素个数
	public long ssize(String key);



}
