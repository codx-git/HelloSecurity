package example.hello_security.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

@Data
//@Repository
@Alias("sys_user")
public class SysUser implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String phone;
    private String address;
    private String avatarUrl;
    private Date createTime;
    private Date lastModifyTime;
    private Date lastLoginTime;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
