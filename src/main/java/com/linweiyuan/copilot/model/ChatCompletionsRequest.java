package com.linweiyuan.copilot.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionsRequest {
    private List<Message> messages;
    private boolean stream;
}
