package com.example.controller;

import com.example.mapper.SysRoleRequestMapper;
import com.example.mapper.SysUserMapper;
import com.example.service.SysUserService;
import com.example.entity.SysUser;
import com.example.mapper.SysRoleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/test")
@Tag(name="testController", description = "测试控制器，学习openapi基础功能")
public class testController {
    @Value("${nickname1:test1}")
    String v1;
    @Value("${nickname2:test2}")
    String v2;
    @Value("${nickname3:test3}")
    String v3;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    SysRoleRequestMapper sysRoleRequestMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysUserMapper sysUserMapper;
   @GetMapping("/list")
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

    @PostMapping("/mybatis")
    //在使用 MyBatis 时，如果 Integer 类型的值为 0，可能会导致动态 SQL 不生效。这是因为 MyBatis 在处理 if 标签时，会将 Integer 类型的 0 值视为空字符串。
    public String mybaitsTest(@RequestParam Map<Object,Object> param){
        sysUserMapper.test(param);
       return "success";
    }
    @GetMapping("getZk")
    public String getZk(){
       return "nickname1"+v1+";nickname2"+v2+";nickname3"+v3;
    }
}
