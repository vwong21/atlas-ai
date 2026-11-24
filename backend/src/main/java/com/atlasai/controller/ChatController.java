package com.atlasai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlasai.dto.ChatRequest;

@RestController
public class ChatController {
    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        String chatId = request.chatId();
        String message = request.message();

        return "Hello World";
    }
}
