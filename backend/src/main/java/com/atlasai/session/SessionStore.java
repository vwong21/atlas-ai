package com.atlasai.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class SessionStore {
    private final Map<String, ChatSession> store = new ConcurrentHashMap<>();

    public ChatSession getOrCreate(String chatId) {
        return store.computeIfAbsent(chatId, id -> new ChatSession(id));
    }

    public void save(ChatSession session) {
        store.put(session.getChatId(), session);
    }

    public void delete(String chatId) {
        store.remove(chatId);
    }
}