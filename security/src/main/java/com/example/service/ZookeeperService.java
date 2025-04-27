package com.example.service;

import java.util.List;

public interface ZookeeperService {
    //创建节点
    public void createNode(String path, String data) throws Exception;
    //获取节点
    public String getNodeData(String path) throws Exception;

    void deleteNode(String path) throws Exception;

    //监听节点变化
    public List<String> watchNode(String path) throws Exception;
}
