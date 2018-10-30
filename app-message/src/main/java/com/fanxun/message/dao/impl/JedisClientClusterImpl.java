package com.fanxun.message.dao.impl;

import com.fanxun.message.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Set;


public class JedisClientClusterImpl implements JedisClient {

	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String hget(String hkey, String key) {
		return jedisCluster.hget(hkey, key);
	}

	@Override
	public long hset(String hkey, String key, String value) {
		return jedisCluster.hset(key, key, value);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public long hdel(String hkey, String key) {
		return jedisCluster.hdel(hkey, key);
	}

	@Override
	public long sadd(String key, String value) {
		return 0;
	}

	@Override
	public long sadd(String key, List<String> values) {
		return 0;
	}

	@Override
	public long srem(String key, String value) {
		return 0;
	}

	@Override
	public long srem(String key, List<String> values) {
		return 0;
	}

	@Override
	public Set<String> smembers(String key) {
		return null;
	}

	@Override
	public long sismenber(String ket, String value) {
		return 0;
	}

	@Override
	public long ssize(String key) {
		return 0;
	}
}
