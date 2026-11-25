package com.atlasai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.atlasai.dto.ClassifyRequest;
import com.atlasai.dto.ClassifyResponse;
import com.atlasai.model.CaseType;
import com.atlasai.session.ChatSession;
import com.atlasai.session.Message;
import com.atlasai.session.Role;
import com.atlasai.session.SessionStore;

@Service
public class ChatPipeline {
    private final SessionStore sessionStore;
    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(ChatPipeline.class);

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

        // 4. Make a request to classifier which returns the caseType
        CaseType classifiedCaseType = classify(messageRequest).caseType();
        // 5. Build a placeholder reply (real logic comes later)

        // 6. Add the assistant's reply to the session history

        // 7. Save the session back to the store

        // 8. Return a ChatResponse with chatId, reply, and the session state

    }

    private ClassifyResponse classify(String messageRequest) {
        try {
            ClassifyResponse response = restClient.post().uri("/classify")
                    .body(new ClassifyRequest(messageRequest))
                    .retrieve().body(ClassifyResponse.class);
            if (response == null) {
                return new ClassifyResponse(CaseType.UNKNOWN);
            }
            return response;
        } catch (RestClientException e) {
            log.error("Classifier call failed", e);
            return new ClassifyResponse(CaseType.UNKNOWN);
        }
    }
}
