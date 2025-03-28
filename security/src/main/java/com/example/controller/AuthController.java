package com.example.controller;

import com.example.service.AuthService;
import com.example.request.AddUserRequest;
import com.example.request.BaseUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/logon")
    //用户登录获取token
    public String userLogon(@RequestBody BaseUserRequest request){
        return authService.userLogon(request.getUsername(),request.getPassword());
    }

    @PostMapping("/logout")
    //用户登出移除token
    public String userLogout(@RequestBody BaseUserRequest request){
        return authService.userLogout(request.getId());
    }
    @PostMapping("/register")
    //注册新用户
    public void registerUser(@RequestBody AddUserRequest user){
        authService.registerUser(user);
        return;
    }
    @PostMapping("/pass")
    public ResponseEntity<?> signUp(String string){
        return ResponseEntity.ok(passwordEncoder.encode(string));
    }
}
