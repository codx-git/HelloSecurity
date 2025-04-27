package com.example.controller;

import com.example.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping("/delete")
    public void deleteNode(@RequestParam String path) throws Exception {
        zookeeperService.deleteNode(path);
    }
    @PostMapping("/watch")
    public List<String> watch(@RequestParam String path) throws Exception {
        return zookeeperService.watchNode(path);
    }
}
