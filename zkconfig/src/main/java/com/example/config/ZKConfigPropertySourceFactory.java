package com.example.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class ZKConfigPropertySourceFactory implements PropertySourceFactory {
    @Autowired
    private CuratorFramework client;
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        try {
            return new ZKConfigPropertySource("/config/hello-security",client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
