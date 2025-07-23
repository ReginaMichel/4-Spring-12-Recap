package org.example.spring12recap.model;

import lombok.Getter;

@Getter
public enum ChangeType {
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String description;

    ChangeType(String description) {
        this.description = description;
    }
}
