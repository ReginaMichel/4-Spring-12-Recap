package org.example.spring12recap.model;

import java.util.List;

public record OpenAiRequest(String model, List<OpenAiMessage> messages) {
}
