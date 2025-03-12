package example.hello_security.service.Impl;

import example.hello_security.entity.SysUser;
import example.hello_security.mapper.SysUserMapper;
import example.hello_security.request.AddUserRequest;
import example.hello_security.service.AuthService;
import example.hello_security.util.ConverterUtils;
import example.hello_security.util.JwtUtils;
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
        try {
            sysUserMapper.insert(sysUser);
        }catch (Exception e){
            log.info("失败" + e);
        }
    }
}
