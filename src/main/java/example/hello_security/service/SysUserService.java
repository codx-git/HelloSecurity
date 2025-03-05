package example.hello_security.service;

import example.hello_security.repository.SysUserRepository;

import java.util.List;

public interface SysUserService {

    List<SysUserRepository> selectAll();
    SysUserRepository loadUserByPhone(String phone);

}
