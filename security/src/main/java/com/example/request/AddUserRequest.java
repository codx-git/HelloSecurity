package com.example.request;

import lombok.Data;

@Data
public class AddUserRequest {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String phone;
    private String address;
    private String avatarUrl;
}
