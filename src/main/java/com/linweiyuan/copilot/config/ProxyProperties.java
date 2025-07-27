package com.linweiyuan.copilot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "proxy")
public class ProxyProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}
