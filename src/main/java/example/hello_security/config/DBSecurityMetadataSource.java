package example.hello_security.config;

import example.hello_security.entity.SysRoleRequest;
import example.hello_security.mapper.SysRoleRequestMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Component
public class DBSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private SysRoleRequestMapper sysRoleRequestMapper;
    @PostConstruct
    public void loadPermissions(){
//        rolePermissionMap = new HashMap<>();
//        List<SysRoleRequest> permissions = sysRoleRequestMapper.findAll();
//        permissions.forEach(perm -> {
//            String key = perm.getRequestMethod() + ":" + perm.getRequestUrl();
//            String role = perm.getRole().getRoleName();
//            ConfigAttribute cfg = new SecurityConfig(role);
//            rolePermissionMap.computeIfAbsent(key, k -> new ArrayList<>()).add(cfg);
//        });
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
