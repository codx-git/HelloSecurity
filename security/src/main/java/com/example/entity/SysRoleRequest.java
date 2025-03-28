package com.example.entity;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "sys_role_request")
public class SysRoleRequest {
    private Integer id;
    private Integer roleId;
    private String code;
    private String requestMethod;
    private String requestUrl;

}
