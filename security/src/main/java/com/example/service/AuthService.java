package com.example.service;

import com.example.request.AddUserRequest;

public interface AuthService {
    public String userLogon(String username,String password);
    public String userLogout(int id);
    public void registerUser(AddUserRequest addUserRequest);
}
