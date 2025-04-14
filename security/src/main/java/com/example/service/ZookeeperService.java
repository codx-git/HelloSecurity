package com.example.service;

public interface ZookeeperService {
    //创建节点
    public void createNode(String path, String data) throws Exception;
    //获取节点
    public String getNodeData(String path) throws Exception;
    //监听节点变化
    public void watchNode(String path) throws Exception;
}
