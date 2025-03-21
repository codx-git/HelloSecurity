package example.hello_security.service;

import example.hello_security.entity.SysUser;
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
