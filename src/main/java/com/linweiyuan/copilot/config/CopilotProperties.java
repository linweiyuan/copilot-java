package com.linweiyuan.copilot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "copilot")
public class CopilotProperties {
    private String token;
    private String chatUrl;
    private String tokenUrl;
    private String userAgent;
    private String editorVersion;
}
