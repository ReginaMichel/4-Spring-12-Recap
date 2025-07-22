package org.example.spring12recap.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    TODO("Todo",1),
    DOING("Doing",2),
    DONE("Done",3);

    private final String text;
    private final int number;

    OrderStatus(String text, int number) {
        this.text = text;
        this.number = number;
    }
}