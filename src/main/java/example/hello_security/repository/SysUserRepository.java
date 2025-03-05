package example.hello_security.repository;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
//@Repository
@Alias("sys_user")
public class SysUserRepository {
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
}
