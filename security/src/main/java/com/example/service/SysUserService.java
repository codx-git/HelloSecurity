package com.example.service;

import com.example.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface SysUserService extends UserDetailsService {

    List<SysUser> selectAll();
    @Deprecated
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException ;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;


}
