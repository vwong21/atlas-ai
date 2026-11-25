package com.atlasai.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CaseType {
    APPLIANCE_CONDITION,
    CANCELLATION,
    GENERAL_QUESTION,
    OFF_TOPIC,
    RETURN_EXCHANGE,
    SERVICE_REQUEST,
    UNKNOWN;

    @JsonCreator
    public static CaseType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        try {
            String normalized = value.trim().toUpperCase();
            return CaseType.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }

    }
}
