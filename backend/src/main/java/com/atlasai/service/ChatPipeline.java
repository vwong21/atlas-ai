package com.atlasai.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.atlasai.dto.ChatResponse;
import com.atlasai.session.ChatSession;
import com.atlasai.session.Message;
import com.atlasai.session.Role;
import com.atlasai.session.SessionState;
import com.atlasai.session.SessionStore;

@Service
public class ChatPipeline {
    private final Map<SessionState, StateHandler> handlers = new EnumMap<>(SessionState.class);
    private final SessionStore sessionStore;

    public ChatPipeline(SessionStore sessionStore, List<StateHandler> handlerList) {
        this.sessionStore = sessionStore;
        for (StateHandler h : handlerList) {
            if (handlers.put(h.state(), h) != null) {
                throw new IllegalStateException("More than one handler for state " + h.state());
            }
        }
        for (SessionState s : SessionState.values()) {
            if (!handlers.containsKey(s)) {
                throw new IllegalStateException("No handler for state " + s);
            }
        }
    }

    public ChatResponse handle(String chatId, String messageRequest) {
        ChatSession session = sessionStore.getOrCreate(chatId);
        session.touch();
        session.addMessage(new Message(Role.CUSTOMER, messageRequest));

        String reply = handlers.get(session.getState()).handleMessage(session, messageRequest);

        session.addMessage(new Message(Role.ASSISTANT, reply));
        sessionStore.save(session);

        return new ChatResponse(chatId, reply, session.getState());
    }
}