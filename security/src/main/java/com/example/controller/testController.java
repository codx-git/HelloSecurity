package com.example.controller;

import com.example.mapper.SysRoleRequestMapper;
import com.example.service.SysUserService;
import com.example.entity.SysUser;
import com.example.mapper.SysRoleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

@Slf4j
@RestController
@RequestMapping("/test")
@Tag(name="testController", description = "测试控制器，学习openapi基础功能")
public class testController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    SysRoleRequestMapper sysRoleRequestMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;

   @GetMapping("list")
   @Operation(summary = "基础list测试类", description = "基础list测试类")
    public String list(){
       log.info("sdfsdf");
       //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
       ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
       log.info("当前线程数量：" + String.valueOf(threadMXBean.getThreadCount()));
       sysRoleRequestMapper.selectAll();
       return "success";
    }
    //@RequestMapping("getOne")
    public SysUser loadUserByPhone(@RequestParam String username){
       //sysUserService.loadUserByUsername(phone);
        return (SysUser)sysUserService.loadUserByUsername(username);
    }
}
