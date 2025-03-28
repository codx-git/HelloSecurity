package com.example.config;

import com.example.entity.SysRoleRequest;
import org.springframework.security.access.SecurityConfig;
import com.example.mapper.SysRoleRequestMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;

@Component
public class DBSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private SysRoleRequestMapper sysRoleRequestMapper;
    private final Map<String, Collection<ConfigAttribute>> rolePermissionMap = new HashMap<>();
    private final AntPathMatcher antMatcher = new AntPathMatcher();
    @PostConstruct
    public void loadPermissions(){
        List<SysRoleRequest> permissions = sysRoleRequestMapper.selectAll();
        permissions.forEach(perm -> {
            String key = perm.getRequestMethod() + ":" + perm.getRequestUrl();
            String role = perm.getCode();
            //import org.springframework.security.access.SecurityConfig;
            ConfigAttribute cfg = new SecurityConfig(role);
            rolePermissionMap.computeIfAbsent(key, k -> new ArrayList<>()).add(cfg);
        });
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String method = fi.getRequest().getMethod();
        String url = fi.getRequestUrl();
        return rolePermissionMap.entrySet().stream()
                .filter(entry -> {
                    String[] parts = entry.getKey().split(":");
                    String storedMethod = parts[0];
                    String storedUrl = parts[1];
                    return ("ALL".equals(storedMethod) || method.equals(storedMethod))
                            && antMatcher.match(storedUrl, url);
                })
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null); // 返回null表示无权限要求
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
