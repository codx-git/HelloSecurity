package com.example.entity;

import lombok.Data;

@Data
public class SysUserOperateLog {
    private Integer id;
    private Integer userId;
    private String username;
    private String ip;
    private String requestUrl;
    private Long costTime;
    private String requestMethod;
}
