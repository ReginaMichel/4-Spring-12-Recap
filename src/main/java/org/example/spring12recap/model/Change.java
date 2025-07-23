package org.example.spring12recap.model;

public record Change(ChangeType changeType, String id, TodoDTO todoDTO) {
}
