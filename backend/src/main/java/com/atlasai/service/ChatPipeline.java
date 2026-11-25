package com.atlasai.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.atlasai.dto.ClassifyRequest;
import com.atlasai.dto.ClassifyResponse;
import com.atlasai.session.ChatSession;
import com.atlasai.session.Message;
import com.atlasai.session.Role;
import com.atlasai.session.SessionStore;

@Service
public class ChatPipeline {
    private final SessionStore sessionStore;
    private final RestClient restClient;

    public ChatPipeline(SessionStore sessionStore, RestClient.Builder restClientBuilder) {
        this.sessionStore = sessionStore;
        this.restClient = restClientBuilder.baseUrl("http://localhost:8000").build();
    }

    public void handle(String chatId, String messageRequest) {
        // 1. Get or create the session from the store using chatId
        ChatSession session = sessionStore.getOrCreate(chatId);
        // 2. touch() the session so its last-activity time updates
        session.touch();
        // 3. Add the customer's message to the session history
        Message message = new Message(Role.CUSTOMER, messageRequest);
        session.addMessage(message);

        // 4. Make a request to classifier which returns the caseType as a String
        ClassifyResponse caseTypeRes = this.restClient.post().uri("/classify").body(new ClassifyRequest(messageRequest))
                .retrieve().body(ClassifyResponse.class);

        // 5. Build a placeholder reply (real logic comes later)

        // 6. Add the assistant's reply to the session history

        // 7. Save the session back to the store

        // 8. Return a ChatResponse with chatId, reply, and the session state

    }
}
