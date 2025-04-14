package com.example.service.Impl;

import com.example.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class ZookeeperServiceImpl implements ZookeeperService {
    @Autowired
    private CuratorFramework curatorFramework;
    @Override
    public void createNode(String path, String data) throws Exception {
        if (curatorFramework.checkExists().forPath(path) == null) {
            curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .forPath(path, data.getBytes());
        }
    }

    @Override
    public String getNodeData(String path) throws Exception {
        byte[] data = curatorFramework.getData().forPath(path);
        return new String(data, StandardCharsets.UTF_8);
    }

    @Override
    public void watchNode(String path) throws Exception {
        CuratorCache cache = CuratorCache.build(curatorFramework,path);
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forCreates(node -> log.info(String.format("Node created: [%s]", node)))
                .forChanges((oldNode, node) -> log.info(String.format("Node changed. Old: [%s] New: [%s]", oldNode, node)))
                .forDeletes(oldNode -> log.info(String.format("Node deleted. Old value: [%s]", oldNode)))
                .forInitialized(() -> log.info("Cache initialized"))
                .build();

        // register the listener
        cache.listenable().addListener(listener);

        // the cache must be started
        cache.start();
//        TreeCache treeCache = new TreeCache(curatorFramework, path);
//        treeCache.start();
//        treeCache.getListenable().addListener((client, event) -> {
//            if (event.getType() == TreeCache.Type.NODE_UPDATED) {
//                String newData = new String(event.getData().getData(), StandardCharsets.UTF_8);
//                log.info("Node updated: " + newData);
//            }
//        });
    }

}
