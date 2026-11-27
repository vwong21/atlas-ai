package com.atlasai.service;

import com.atlasai.session.ChatSession;
import com.atlasai.session.SessionState;

public interface StateHandler {
    SessionState state();

    String handleMessage(ChatSession session, String message);
}
