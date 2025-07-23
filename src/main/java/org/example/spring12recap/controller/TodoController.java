package org.example.spring12recap.controller;

import org.example.spring12recap.model.Todo;
import org.example.spring12recap.model.TodoDTO;
import org.example.spring12recap.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Todo addTodo(@RequestBody TodoDTO todoDTO) {
        return service.addTodo(todoDTO);
    }

    @GetMapping("/{id}")
    public Todo getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Todo updateById(@PathVariable String id, @RequestBody TodoDTO todoDTO) {
        return service.updateById(id, todoDTO);
    }
}
