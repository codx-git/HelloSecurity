package com.example.service.Impl;

import com.example.entity.SysUser;
import com.example.mapper.SysUserMapper;
import com.example.request.AddUserRequest;
import com.example.service.AuthService;
import com.example.type.UserType;
import com.example.utils.ConverterUtils;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String userLogon(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //登录时间
        sysUserMapper.updateLastLoginTime(username);
        return jwtUtils.generateToken((SysUser) authentication.getPrincipal());
    }

    @Override
    public String userLogout(int id) {
        return null;
    }

    @Override
    public void registerUser(AddUserRequest addUserRequest) {
        SysUser sysUser = new SysUser();
        ConverterUtils.convert(addUserRequest,sysUser);
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUser.setCreateTime(new Date());
        sysUser.setLastModifyTime(new Date());
        sysUser.setLastLoginTime(new Date());
        sysUser.setAccountNonExpired(UserType.USER_ACCOUNT_NOT_EXPIRED.getKey());
        sysUser.setAccountNonLocked(UserType.USER_ACCOUNT_NOT_LOCKED.getKey());
        sysUser.setCredentialsNonExpired(UserType.USER_CREDENTIALS_NOT_EXPIRED.getKey());
        sysUser.setEnabled(UserType.USER_IS_ENABLED.getKey());
        try {
            sysUserMapper.insert(sysUser);
        }catch (Exception e){
            log.info("失败" + e);
        }
    }
}
