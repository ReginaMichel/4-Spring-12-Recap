package org.example.spring12recap.service;

import org.example.spring12recap.model.Todo;
import org.example.spring12recap.model.TodoDTO;
import org.example.spring12recap.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {
    private final TodoRepository repo;
    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public List<Todo> getAll() {
        return repo.findAll();
    }

    public Todo addTodo(TodoDTO todoDTO) {
        String newID = UUID.randomUUID().toString();
        repo.save(new Todo(newID,
                todoDTO.description(),
                todoDTO.status()));
        return repo.findById(newID).orElse(null);
    }

    public Todo getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Todo updateById(String id, TodoDTO todoDTO) {
        repo.save(new Todo(id, todoDTO.description(), todoDTO.status()));
        return repo.findById(id).orElse(null);
    }

    public Todo deleteById(String id) {
        Todo toBeRemoved = repo.findById(id).orElse(null);
        repo.deleteById(id);
        return toBeRemoved;
    }
}
