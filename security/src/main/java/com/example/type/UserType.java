package com.example.type;

public enum UserType {
    USER_ACCOUNT_NOT_EXPIRED(true,"用户未过期"),
    USER_ACCOUNT_IS_EXPIRED(false,"用户已过期"),
    USER_ACCOUNT_NOT_LOCKED(true,"用户未被锁定"),
    USER_ACCOUNT_IS_LOCKED(false,"用户已被锁定"),
    USER_CREDENTIALS_NOT_EXPIRED(true,"用户密码未过期"),
    USER_CREDENTIALS_IS_EXPIRED(false,"用户密码已过期"),
    USER_IS_ENABLED(true,"用户已启用"),
    USER_NOT_ENABLED(false,"用户未启用");
    private final Boolean key;
    private final String value;

    public Boolean getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    UserType(Boolean key, String value) {
        this.key = key;
        this.value = value;
    }
}
