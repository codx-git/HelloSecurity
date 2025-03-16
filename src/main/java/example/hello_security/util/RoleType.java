package example.hello_security.util;

public class RoleType {


    private final String key;
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    RoleType(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
