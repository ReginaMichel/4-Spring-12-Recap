package org.example.spring12recap.model;

import java.util.List;

public record OpenAiResponse(List<OpenAiChoice> choices) {
}
