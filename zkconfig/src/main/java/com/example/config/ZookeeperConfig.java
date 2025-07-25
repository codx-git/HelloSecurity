package com.example.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;

@Configuration
public class ZookeeperConfig {
    @Value("${zookeeper.connect-string:localhost:2181}")
    private String connectString;
    @Value("${zookeeper.session-timeout:3000}")
    private int sessionTimeout;
    @Value("${zookeeper.namespace:}")
    private String namespace;

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "zookeeper.enable", havingValue = "true", matchIfMissing = false)
    public CuratorFramework curatorFramework(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                connectString,
                sessionTimeout,
                sessionTimeout,
                retryPolicy
        );
        //异常： Expected state [STARTED] was [LATENT]，是由于client没有使用start()
        client.start();
        client.usingNamespace(namespace); // 使用命名空间隔离环境
        return client;
    }
    @Bean
    @ConditionalOnProperty(name = "zookeeper.enable", havingValue = "true", matchIfMissing = false)
    public PropertySource<CuratorFramework> zkConfigPropertySource(CuratorFramework curatorFramework, @Value("${spring.application.name}") String serviceName ) throws Exception {
        String path = "/config/" + serviceName;
        return new ZKConfigPropertySource(path,curatorFramework);
    }
}
