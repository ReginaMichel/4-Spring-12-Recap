package org.example.spring12recap.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    OPEN("OPEN"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String text;

    OrderStatus(String text) {
        this.text = text;
    }
}