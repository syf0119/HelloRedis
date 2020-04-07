package com.it.like;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public class TestRedis {
    private JedisPool jedisPool = null;

    @Before
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMinIdle(5);
        config.setMaxTotal(10);
        config.setMaxWaitMillis(3000);

        jedisPool = new JedisPool(config, "spring", 6379);
    }

    @Test
    public void jedisString() {
        Jedis jedis = jedisPool.getResource();
        jedis.set("a", "1");
        jedis.incr("a");
        jedis.decr("a");
        jedis.append("a", "11111111");
        jedis.expire("a", 5);
        String res = jedis.get("a");
        System.out.println(res);
    }

    @Test
    public void jedisList() {
        Jedis jedis = jedisPool.getResource();
        jedis.lpush("ls", "a", "b");


        List<String> ls = jedis.lrange("ls", 0, -1);
        System.out.println(ls);
    }

    @Test
    public void jedisHash() {
        Jedis jedis = jedisPool.getResource();
        jedis.hset("hash", "name", "jack");
        String hget = jedis.hget("hash", "name");
        System.out.println(hget);
    }

    @After
    public void close() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}
