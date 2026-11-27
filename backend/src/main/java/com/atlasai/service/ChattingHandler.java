package com.atlasai.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.atlasai.dto.ClassifyRequest;
import com.atlasai.dto.ClassifyResponse;
import com.atlasai.model.Case;
import com.atlasai.model.CaseType;
import com.atlasai.session.ChatSession;
import com.atlasai.session.SessionState;

@Component
public class ChattingHandler implements StateHandler {
    private static final Logger log = LoggerFactory.getLogger(ChattingHandler.class);

    private static final List<String> CANCELLATION_OPENERS = List.of(
            "I can help with that. Please fill in the details below and I'll get your cancellation request ready.",
            "Sure, I can help you cancel an order. Please provide the details below and I'll take it from there.",
            "Thanks for letting me know. Please enter the details below so I can set up your cancellation request.");

    private final RestClient restClient;

    public ChattingHandler(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("http://localhost:8000").build();
    }

    @Override
    public SessionState state() {
        return SessionState.CHATTING;
    }

    @Override
    public String handleMessage(ChatSession session, String message) {
        CaseType type = classify(message).caseType();

        switch (type) {
            case CANCELLATION -> {
                openCase(session, type);
                return pick(CANCELLATION_OPENERS);
            }
            case RETURN_EXCHANGE, SERVICE_REQUEST, APPLIANCE_CONDITION -> {
                openCase(session, type);
                return "Placeholder opener for " + type;
            }
            case OFF_TOPIC -> {
                return "I can only help with orders and appliance service.";
            }
            case GENERAL_QUESTION -> {
                return "Placeholder general answer";
            }
            default -> {
                return "Could you tell me a bit more about what you need help with?";
            }
        }
    }

    private void openCase(ChatSession session, CaseType type) {
        Case newCase = new Case();
        newCase.setCaseType(type);
        session.addCase(newCase);
        session.setState(SessionState.COLLECTING);
    }

    private String pick(List<String> options) {
        return options.get(ThreadLocalRandom.current().nextInt(options.size()));
    }

    private ClassifyResponse classify(String message) {
        try {
            ClassifyResponse response = restClient.post().uri("/classify")
                    .body(new ClassifyRequest(message))
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