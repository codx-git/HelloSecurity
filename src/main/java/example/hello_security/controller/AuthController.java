package example.hello_security.controller;

import example.hello_security.request.AddUserRequest;
import example.hello_security.request.BaseUserRequest;
import example.hello_security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @RequestMapping("/logon")
    //用户登录获取token
    public String userLogon(@RequestBody BaseUserRequest request){
        return authService.userLogon(request.getUsername(),request.getPassword());
    }

    @RequestMapping("/logout")
    //用户登出移除token
    public String userLogout(@RequestBody BaseUserRequest request){
        return authService.userLogout(request.getId());
    }
    @RequestMapping("/register")
    //注册新用户
    public void registerUser(@RequestBody AddUserRequest user){
        authService.registerUser(user);
        return;
    }
    @RequestMapping("/pass")
    public ResponseEntity<?> signUp(String string){
        return ResponseEntity.ok(passwordEncoder.encode(string));
    }
}
