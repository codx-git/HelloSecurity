package example.hello_security.util;

public enum UserType {
    USER_ACCOUNT_NOT_EXPIRED(0,"用户未过期"),
    USER_ACCOUNT_IS_EXPIRED(1,"用户已过期"),
    USER_ACCOUNT_NOT_LOCKED(0,"用户未被锁定"),
    USER_ACCOUNT_IS_LOCKED(1,"用户已被锁定"),
    USER_CREDENTIALS_NOT_EXPIRED(0,"用户密码未过期"),
    USER_CREDENTIALS_IS_EXPIRED(1,"用户密码已过期"),
    USER_NOT_ENABLED(0,"用户未启用"),
    USER_IS_ENABLED(1,"用户已启用");
    private final Integer key;
    private final String Value;

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return Value;
    }

    UserType(Integer key, String value) {
        this.key = key;
        this.Value = value;
    }
}
