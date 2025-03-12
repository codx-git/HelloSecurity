package example.hello_security.request;

import lombok.Data;

import java.util.Map;

@Data
public class EntityRequest {
    private Map<String,Object> request;
}
