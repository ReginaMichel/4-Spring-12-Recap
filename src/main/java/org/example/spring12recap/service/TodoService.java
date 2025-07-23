package org.example.spring12recap.service;

import org.example.spring12recap.model.Todo;
import org.example.spring12recap.model.TodoDTO;
import org.example.spring12recap.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repo;
    private final IdService idService;
    public TodoService(TodoRepository repo, IdService idService) {
        this.repo = repo;
        this.idService = idService;
    }

    public List<Todo> getAll() {
        return repo.findAll();
    }

    public Todo addTodo(TodoDTO todoDTO) {
        String newID = idService.generateID();
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
