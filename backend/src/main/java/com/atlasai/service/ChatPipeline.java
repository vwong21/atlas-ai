package com.atlasai.service;

import org.springframework.stereotype.Service;

import com.atlasai.session.SessionStore;

@Service
public class ChatPipeline {
    private final SessionStore sessionStore;

    public ChatPipeline(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    public void handle(String chatId, String message) {
        // 1. Get or create the session from the store using chatId

        // 2. touch() the session so its last-activity time updates

        // 3. Add the customer's message to the session history

        // 4. Build a placeholder reply (real logic comes later)

        // 5. Add the assistant's reply to the session history

        // 6. Save the session back to the store

        // 7. Return a ChatResponse with chatId, reply, and the session state

    }
}
