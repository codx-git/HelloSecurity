package example.hello_security.request;

import lombok.Data;

@Data
public class BaseUserRequest {
    private Integer id;
    private String username;
    private String phone;
    private String password;
}
