package example.hello_security.controller;

import example.hello_security.repository.SysUserRepository;
import example.hello_security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class testController {
    @Autowired
    private SysUserService sysUserService;

   @RequestMapping("list")
    public List<SysUserRepository> list(){
       return sysUserService.selectAll();
    }
    @RequestMapping("getOne")
    public SysUserRepository loadUserByPhone(@RequestParam String phone){
        return sysUserService.loadUserByPhone(phone);
    }
}
