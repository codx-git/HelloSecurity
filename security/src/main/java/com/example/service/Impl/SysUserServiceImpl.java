package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.SysUser;
import com.example.mapper.SysRoleMapper;
import com.example.mapper.SysUserMapper;
import com.example.service.SysUserService;
import com.example.type.SystemType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysUser> selectAll() {
        //log.info("This is a logger test {}:{}",1,2);
        return sysUserMapper.selectAll();
    }

    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        if(user == null){
            throw new UsernameNotFoundException(phone + SystemType.USER_NOT_FOUND.getValue());
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.selectByUsername(username);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException(username + SystemType.USER_NOT_FOUND.getValue());
        }
        user.setRoles(sysRoleMapper.roleByUsername(username));
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toSet());
        user.setAuthorities(authorities);
        //log.info(user.getAuthorities().toString());
        return user;
    }


}
