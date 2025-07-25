package com.example.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.springframework.core.env.EnumerablePropertySource;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public final class ZKConfigPropertySource extends EnumerablePropertySource<CuratorFramework> {
    private static final String PROPERTY_NAME = "zkConfig";
    private String path;
    private Map<String,Object> properties;
    public ZKConfigPropertySource(String path, CuratorFramework source) throws Exception {
        super(PROPERTY_NAME, source);
        this.path = path;
        this.properties = loadProperties();
    }

    private Map<String, Object> loadProperties() {
        Map<String, Object> properties = new HashMap<>();
        try {
            // 递归遍历 ZooKeeper 节点
            byte[] data = source.getData().forPath(path);
            if(data == null || data.length == 0){
                log.error("从 Zookeeper 获取的配置数据为空");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            properties = objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            logger.error("Failed to load properties from ZooKeeper", e);
        }
        return properties;
    }
//    private void startWatching() {
//        try {
//            // 注册节点变更监听
//            CuratorCache cache = new CuratorCache(getSource(), PROPERTY_NAME, true);
//            cache.start(CuratorCache.StartMode.POST_INITIALIZED_EVENT);
//
//            cache.getListenable().addListener((client, event) -> {
//                // 配置变更时重新加载
//                refreshProperties();
//                logger.info("Configuration refreshed due to ZooKeeper change");
//            });
//        } catch (Exception e) {
//            logger.error("Failed to start watching ZooKeeper path", e);
//        }
//    }
//    private void refreshProperties() {
//        // 重新加载配置
//        Map<String, Object> newProperties = loadProperties();
//        synchronized (this.properties) {
//            this.properties.clear();
//            this.properties.putAll(newProperties);
//        }
//    }

    @Override
    public String[] getPropertyNames() {
        return this.properties.keySet().toArray(new String[0]);
    }

    @Override
    public Object getProperty(String name) {
        return this.properties.get(name);
    }
}
