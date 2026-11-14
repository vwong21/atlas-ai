package com.atlasai.session;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.atlasai.model.Case;
import com.atlasai.model.CaseStatus;
import com.atlasai.model.CustomerInfo;

public class ChatSession {
    private final String chatId;
    private final Instant createdAt = Instant.now();
    private Instant lastModified = Instant.now();
    private SessionState state = SessionState.CHATTING;
    private final List<Message> messages = new ArrayList<>();
    private final List<Case> cases = new ArrayList<>();
    private final CustomerInfo customerInfo = new CustomerInfo();

    public ChatSession(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void touch() {
        lastModified = Instant.now();
    }

    public SessionState getState() {
        return state;
    }

    public void setState(SessionState state) {
        this.state = state;
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Case> getCases() {
        return Collections.unmodifiableList(cases);
    }

    public void addCase(Case newCase) {
        cases.add(newCase);
    }

    public Case getActiveCase() {
        if (cases.isEmpty()) {
            return null;
        }
        Case last = cases.get(cases.size() - 1);
        return last.getCaseStatus() == CaseStatus.COLLECTING ? last : null;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }
}
