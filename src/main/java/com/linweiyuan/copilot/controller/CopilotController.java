package com.linweiyuan.copilot.controller;

import com.linweiyuan.copilot.model.ChatCompletionsRequest;
import com.linweiyuan.copilot.service.CopilotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class CopilotController {
    private final CopilotService copilotService;

    @PostMapping
    public Flux<String> chatCompletions(@RequestBody ChatCompletionsRequest chatCompletionsRequest) {
        return copilotService.chatCompletions(chatCompletionsRequest);
    }
}
