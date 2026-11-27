package com.atlasai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlasai.dto.ChatRequest;
import com.atlasai.dto.ChatResponse;
import com.atlasai.service.ChatPipeline;

@RestController
public class ChatController {
    private final ChatPipeline chatPipeline;

    public ChatController(ChatPipeline chatPipeline) {
        this.chatPipeline = chatPipeline;
    }

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String chatId = request.chatId();
        String message = request.message();

        return chatPipeline.handle(chatId, message);
    }
}
