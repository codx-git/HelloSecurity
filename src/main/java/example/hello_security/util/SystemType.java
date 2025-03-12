package example.hello_security.util;

public enum SystemType {
    USER_NOT_FOUND(1000110 , "用户不存在"),
    USER_NOT_AUTHENTICATE(1000120 , "用户无权限"),
    TOKEN_FAILED(1000130,"TOKEN验证失败"),
    USER_AUTHENTICATE_FAILED(1000140,"用户授权失败"),
    CONVERT_FAILED(1000150,"转化失败");
    private final Integer key;
    private final String value;

    SystemType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
