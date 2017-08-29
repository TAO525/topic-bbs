package com.wayne;

import com.wayne.config.SecurityConst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @Author TAO
 * @Date 2017/7/28 14:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    JedisPool jedisPool;

    @Test
    public void testSet(){
        String uuid = UUID.randomUUID().toString();
        System.out.println("jedisPool uuid : " + uuid);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(uuid, 1000, "hahaha");
        }
    }

    @Test
    public void testincr(){
        try (Jedis jedis = jedisPool.getResource()) {
            for (int i=0; i<1000;i++ ) {
                if (jedis.exists(SecurityConst.LIMITS_ + "1.1.1.1")) {
                    jedis.incr(SecurityConst.LIMITS_ + "1.1.1.1");
                } else {
                    jedis.incr(SecurityConst.LIMITS_ + "1.1.1.1");
                    jedis.expire(SecurityConst.LIMITS_ + "1.1.1.1", 6000);
                }
            }
        }
    }

    @Test
    public void testGet(){

        try (Jedis jedis = jedisPool.getResource()) {
            String s = jedis.get("aaaaa");
            System.out.println(s);
        }
    }


    @Test
    public void testHacks(){
        try (Jedis jedis = jedisPool.getResource()) {

           for(int i=0;i<3;i++) {
               String uuid = UUID.randomUUID().toString();

               jedis.lpush(SecurityConst.HACKS_, uuid);
           }
        }
    }
}
