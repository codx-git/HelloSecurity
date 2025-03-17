package example.hello_security.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import example.hello_security.entity.SysUser;
import example.hello_security.mapper.SysRoleMapper;
import example.hello_security.mapper.SysUserMapper;
import example.hello_security.request.AddUserRequest;
import example.hello_security.service.SysUserService;
import example.hello_security.util.ConverterUtils;
import example.hello_security.util.JwtUtils;
import example.hello_security.util.SystemType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
        user.setRoles(sysRoleMapper.selectByUsername(username));
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toList());
        if(user == null){
            throw new UsernameNotFoundException(username + SystemType.USER_NOT_FOUND.getValue());
        }
        return user;
    }


}
