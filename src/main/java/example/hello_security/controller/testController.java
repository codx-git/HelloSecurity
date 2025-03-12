package example.hello_security.controller;

import example.hello_security.entity.SysUser;
import example.hello_security.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/test")
public class testController {
    @Autowired
    private SysUserService sysUserService;

   @RequestMapping("list")
    public List<SysUser> list(){
       log.info("sdfsdf");
       return sysUserService.selectAll();
    }
    //@RequestMapping("getOne")
    public SysUser loadUserByPhone(@RequestParam String username){
       //sysUserService.loadUserByUsername(phone);
        return (SysUser)sysUserService.loadUserByUsername(username);
    }
}
