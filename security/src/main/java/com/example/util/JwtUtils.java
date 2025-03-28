package com.example.util;


import com.example.type.SystemType;
import com.example.entity.SysUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    //自定义jwt密钥
    @Value("${jwt.secret}")
    private String jwtSecret;
    //设置密钥有效期
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    private Key hmacKey;
//    public JwtUtils() {
//        // 由于 main 方法测试时无法注入 @Value，这里先硬编码示例值
//        this.jwtSecret = "mySecretKeyForJWTGeneration12345mySecretKeyForJWTGeneration12345";
//        this.jwtExpiration = 3600000L; // 1 小时
//        this.hmacKey = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
//    }
    @PostConstruct
    //使用 @PostConstruct 注解在属性注入完成后初始化 hmacKey
    private void initHmacKey(){
       hmacKey = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    //生成 Token
    public String generateToken(SysUser sysUser){
        //hmacKey = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(sysUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(hmacKey,SignatureAlgorithm.HS512)
                .compact();
    }

    // 验证 Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error(SystemType.TOKEN_FAILED.getValue() + e);
            return false;
        }
    }
    // 从 Token 获取用户名
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


//    public static void main(String[] args) {
//        JwtUtils jwtUtils = new JwtUtils();
//        SysUser user = new SysUser();
//		user.setUsername("test");
//        String token = jwtUtils.generateToken(user);
//        System.out.println("token:"+token);
//        // 验证 Token
//        boolean isValid = jwtUtils.validateToken(token);
//        System.out.println("Token is valid: " + isValid);
//        System.out.println("username: " + jwtUtils.getUsernameFromToken(token));
//
//    }
}
