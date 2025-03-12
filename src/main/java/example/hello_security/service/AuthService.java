package example.hello_security.service;

import example.hello_security.request.AddUserRequest;

public interface AuthService {
    public String userLogon(String username,String password);
    public String userLogout(int id);
    public void registerUser(AddUserRequest addUserRequest);
}
