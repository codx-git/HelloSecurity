//package com.example.config;
//
//import com.example.entity.SysRoleRequest;
//import com.example.entity.SysUser;
//import com.example.mapper.SysRoleRequestMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.function.Supplier;
//@Component
//public class SysAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
//    @Autowired
//    SysRoleRequestMapper sysRoleRequestMapper;
//    @Override
//    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//        HttpServletRequest request = object.getRequest();
//        String requestURI = request.getRequestURI();
//        String method = request.getMethod();
//        Object principal = authentication.get().getPrincipal();
//        String username = (((SysUser) principal).getUsername());
////        if(principal instanceof User){
////            String username = (((User) principal).getUsername());
////        }
//        Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
//        //使用permissionByUsernameAndUrl的弊端无法正则匹配
//        boolean hasPermission = sysRoleRequestMapper.permissionByUsernameAndUrl(username,requestURI,method);
////        for(GrantedAuthority authority : authorities){
////            String authorityname = authority.getAuthority();
////            SysRoleRequest sysRoleRequest =
////        }
//        return new AuthorizationDecision(hasPermission);
//    }
//}
