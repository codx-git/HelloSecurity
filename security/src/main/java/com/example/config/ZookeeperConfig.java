package com.example.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {
    @Value("${zookeeper.connect-string}")
    private String connectString;
    @Value("${zookeeper.session-timeout:3000}")
    private int sessionTimeout;
    @Value("${zookeeper.namespace:}")
    private String namespace;

    @Bean(initMethod = "start",destroyMethod = "close")
    public CuratorFramework curatorFramework(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                connectString,
                sessionTimeout,
                sessionTimeout,
                retryPolicy
        );
        client.usingNamespace(namespace); // 使用命名空间隔离环境
        return client;
    }
}
