package com.example.config;

import com.example.filter.JwtAuthenticationFilter;
import com.example.filter.LogTraceIdFilter;
import com.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private LogTraceIdFilter logTraceIdFilter;
    @Autowired
    private SysUserService userDetailsService;
    @Autowired
    SysAuthorizationManager sysAuthorizationManager;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //.requestMatchers("/auth/**").permitAll()
                        .requestMatchers(
                                "/auth/**","/error","/test/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().access(sysAuthorizationManager)
//                        .withObjectPostProcessor(
//                                new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                                    @Override
//                                    public <O extends FilterSecurityInterceptor> O postProcess(O interceptor) {
//                                        interceptor.setSecurityMetadataSource(securityMetadataSource);
//                                        interceptor.setAccessDecisionManager(accessDecisionManager());
//                                        return interceptor;
//                                    }
//                                }
//                        )
                )
                .addFilterBefore(logTraceIdFilter, DisableEncodeUrlFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
//    @Bean
//    public AccessDecisionManager accessDecisionManager() {
//        return new AffirmativeBased(List.of(new RoleVoter()));
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
