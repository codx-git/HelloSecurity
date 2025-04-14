package com.example.controller;

import com.example.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zk")
public class ZookeeperController {
    @Autowired
    private ZookeeperService zookeeperService;

    @PostMapping("/creat")
    public String create(@RequestParam String path,@RequestParam String data) throws Exception {
        zookeeperService.createNode(path,data);
        return "success";
    }

    @GetMapping("/get")
    public String getNode(@RequestParam String path) throws Exception {
        return zookeeperService.getNodeData(path);
    }

    @PostMapping("/watch")
    public String watch(@RequestParam String path) throws Exception {
        zookeeperService.watchNode(path);
        return "success";
    }
}
