package com.wayne.task;

import com.wayne.config.SecurityConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Author TAO
 * @Date 2017/7/29 22:01
 * 定时任务
 */
@Component
public class TopicScheduled {

    private static final Logger logger = LoggerFactory.getLogger(TopicScheduled.class);

    @Autowired
    JedisPool jedisPool;

    @Scheduled(cron = "0 0 0 * * ?")
    public void executeFileDownLoadTask() {

        try (Jedis resource = jedisPool.getResource()){
            List<String> lrange = resource.lrange(SecurityConst.HACKS_, 0, -1);
            logger.info("定时任务清除黑名单:"+lrange.toString());
            resource.ltrim(SecurityConst.HACKS_,1,0);//清空黑名单
        }
    }
}
