package com.atlasai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.atlasai.dto.ChatResponse;
import com.atlasai.dto.ClassifyRequest;
import com.atlasai.dto.ClassifyResponse;
import com.atlasai.model.CaseType;
import com.atlasai.session.ChatSession;
import com.atlasai.session.Message;
import com.atlasai.session.Role;
import com.atlasai.session.SessionState;
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

    public ChatResponse handle(String chatId, String messageRequest) {
        // Get or create session
        ChatSession session = sessionStore.getOrCreate(chatId);
        session.touch();

        // Create new Message and add the request message to session
        Message message = new Message(Role.CUSTOMER, messageRequest);
        session.addMessage(message);

        // Pass message through classifier, giving us a case type
        CaseType classifiedCaseType = classify(messageRequest).caseType();

        // If a valid case type, change session state to COLLECTING
        if (classifiedCaseType != CaseType.GENERAL_QUESTION && classifiedCaseType != CaseType.OFF_TOPIC
                && classifiedCaseType != CaseType.UNKNOWN)
            session.setState(SessionState.COLLECTING);

        String reply = buildReply(classifiedCaseType, session);
        session.addMessage(new Message(Role.ASSISTANT, reply));
        sessionStore.save(session);

        return new ChatResponse(chatId, reply, session.getState());
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

    private String buildReply(CaseType classifiedCaseType, ChatSession session) {
        if (classifiedCaseType != CaseType.CANCELLATION) {
            if (session.getState() == SessionState.CHATTING) {
                List<String> responses = new ArrayList<>(List.of(
                        "I can help with that. Please fill in the details below and I'll get your cancellation request ready.",
                        "Sure, I can help you cancel an order. Please provide the details below and I'll take it from there.",
                        "Thanks for letting me know. Please enter the details below so I can set up your cancellation request."));
                return responses.get(ThreadLocalRandom.current().nextInt(responses.size()));
            }

        }
        return "Placeholder String";
    }
}
