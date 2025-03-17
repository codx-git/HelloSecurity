package example.hello_security.entity;

import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Table(name = "sys_role")
public class SysRole implements GrantedAuthority {
    private Integer id;
    private String code;
    private String roleName;
    @Override
    public String getAuthority() {
        return null;
    }
}
