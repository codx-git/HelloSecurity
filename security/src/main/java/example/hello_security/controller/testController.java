package example.hello_security.controller;

import example.hello_security.entity.SysUser;
import example.hello_security.mapper.SysRoleMapper;
import example.hello_security.mapper.SysRoleRequestMapper;
import example.hello_security.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@RestController
@RequestMapping("/test")
public class testController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    SysRoleRequestMapper sysRoleRequestMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;

   @RequestMapping("list")
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
