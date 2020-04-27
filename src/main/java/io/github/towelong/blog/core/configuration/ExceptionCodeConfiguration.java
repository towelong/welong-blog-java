package io.github.towelong.blog.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "v")
@PropertySource(value = "classpath:config/exception-code.properties")
@Component
public class ExceptionCodeConfiguration {

    private Map<Integer,String> codes= new HashMap<>();

    public String getMessage(int code){
        String message = codes.get(code);
        return message;
    }

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }
}
