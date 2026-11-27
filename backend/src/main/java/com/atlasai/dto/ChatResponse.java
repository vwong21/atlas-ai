package com.atlasai.dto;

import com.atlasai.session.SessionState;

public record ChatResponse(String chatId, String reply, SessionState sessionState) {

}
