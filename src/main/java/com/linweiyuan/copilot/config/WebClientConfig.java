package com.linweiyuan.copilot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@RequiredArgsConstructor
@EnableConfigurationProperties(ProxyProperties.class)
@Configuration
public class WebClientConfig {
    private final CopilotProperties copilotProperties;
    private final ProxyProperties proxyProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("User-Agent", copilotProperties.getUserAgent())
                .defaultHeader("Editor-Version", copilotProperties.getEditorVersion())
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
//                                        .proxy(typeSpec -> typeSpec.type(ProxyProvider.Proxy.HTTP)
//                                                .host(proxyProperties.getHost())
//                                                .port(proxyProperties.getPort())
//                                                .username(proxyProperties.getUsername())
//                                                .password(s -> proxyProperties.getPassword())
//
//                                        )
                        )
                )
                .build();
    }
}
