package com.linweiyuan.copilot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linweiyuan.copilot.config.CopilotProperties;
import com.linweiyuan.copilot.model.ChatCompletionsRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(CopilotProperties.class)
@Service
public class CopilotService {
    private final CopilotProperties copilotProperties;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Getter
    private volatile String token;

    @Scheduled(cron = "0 */${copilot.token-refresh-interval} * * * *")
    public void refreshToken() {
        log.info("refreshing copilot token at: {}", LocalDateTime.now());

        webClient.get()
                .uri(copilotProperties.getTokenUrl())
                .header("User-Agent", copilotProperties.getUserAgent())
                .header("Authorization", "token " + copilotProperties.getToken())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(s -> {
                    try {
                        token = objectMapper.readValue(s, Map.class).get("token").toString();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnError(throwable -> log.error("failed to refresh token", throwable))
                .subscribe();
    }

    public Flux<String> chatCompletions(ChatCompletionsRequest chatCompletionsRequest) {
        return webClient.post()
                .uri(copilotProperties.getChatUrl())
                .header("Authorization", "Bearer " + token)
                .header("Accept", chatCompletionsRequest.isStream() ? MediaType.TEXT_EVENT_STREAM_VALUE : MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(chatCompletionsRequest)
                .retrieve()
                .bodyToFlux(String.class);
    }
}
