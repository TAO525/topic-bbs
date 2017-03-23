package com.wayne.config;

import java.beans.PropertyVetoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @Author TAO
 * @Date 2017/3/23 17:28
 */
@Configuration
public class DBConfig {

    @Autowired
    private Environment env;

    @Bean(name="dataSource")
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getProperty("bbs.driver"));
        dataSource.setJdbcUrl(env.getProperty("bbs.dbUrl"));
        dataSource.setUser(env.getProperty("bbs.dbUserName"));
        dataSource.setPassword(env.getProperty("bbs.driver"));
        //连接池中保留的最大连接数
        dataSource.setMaxPoolSize(20);
        //连接池中保留的最小连接数
        dataSource.setMinPoolSize(5);
        //初始化连接数
        dataSource.setInitialPoolSize(10);
        //最大空闲时间,300秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
        dataSource.setMaxIdleTime(300);
        //当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。
        dataSource.setAcquireIncrement(5);
        //每60秒检查所有连接池中的空闲连接。
        dataSource.setIdleConnectionTestPeriod(60);

        return dataSource;
    }

}
