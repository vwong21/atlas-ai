package com.atlasai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @PostMapping("/chat")
    String chat() {
        return "Hello World";
    }
}
