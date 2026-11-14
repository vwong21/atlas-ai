package com.atlasai.session;

import java.time.Instant;

public class Message {
    private final Role role;
    private final String content;
    private final Instant timestamp = Instant.now();

    public Message(Role role, String content) {
        this.role = role;
        this.content = content;
    }

    public Role getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
