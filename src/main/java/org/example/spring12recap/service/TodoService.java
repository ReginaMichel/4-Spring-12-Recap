package org.example.spring12recap.service;

import org.example.spring12recap.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository repo;
    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }
}
