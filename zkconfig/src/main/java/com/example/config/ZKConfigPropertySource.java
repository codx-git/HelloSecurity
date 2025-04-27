package com.example.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.core.env.EnumerablePropertySource;

import java.util.Map;

public class ZKConfigPropertySource extends EnumerablePropertySource<CuratorFramework> {
    private static final String PROPERTY_NAME = "zkConfig";
    private String path;
    private Map<String,Object> config;
    public ZKConfigPropertySource(String path, CuratorFramework source) throws Exception {
        super(PROPERTY_NAME, source);
        byte[] data = source.getData().forPath(path);
        //this.config.
    }

    @Override
    public String[] getPropertyNames() {
        return new String[0];
    }

    @Override
    public Object getProperty(String name) {
        return null;
    }
}
