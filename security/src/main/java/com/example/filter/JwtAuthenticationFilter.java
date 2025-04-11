package com.example.filter;

import com.example.entity.SysUserOperateLog;
import com.example.mapper.SysUserOperateLogMapper;
import com.example.service.SysUserService;
import com.example.type.SystemType;
import com.example.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    SysUserOperateLogMapper sysUserOperateLogMapper;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //log.info("running jwt start");
            long costStart = System.currentTimeMillis();
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateToken(jwt)){
                //jwt验证
                String username = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = sysUserService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                //用户认证后记录对应请求记录
                SysUserOperateLog sysUserOperateLog = new SysUserOperateLog();
                sysUserOperateLog.setUsername(username);
                sysUserOperateLog.setIp(request.getRemoteAddr());
                sysUserOperateLog.setRequestUrl(request.getRequestURI());
                sysUserOperateLog.setRequestMethod(request.getMethod());
                sysUserOperateLog.setCostTime(System.currentTimeMillis() - costStart);
                sysUserOperateLog.setCreateTime(new Date());
                sysUserOperateLogMapper.insert(sysUserOperateLog);
            }
        }catch (Exception e){
            log.error(SystemType.USER_AUTHENTICATE_FAILED.getValue() + e);
        }
        filterChain.doFilter(request, response);
        //log.info("running jwt end");
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
