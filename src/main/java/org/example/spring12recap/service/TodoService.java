package org.example.spring12recap.service;

import lombok.Getter;
import org.example.spring12recap.model.*;
import org.example.spring12recap.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repo;
    private final IdService idService;
    @Getter
    private final LinkedHashMap<Integer, Change> history;
    private int counter;

    public TodoService(TodoRepository repo, IdService idService) {
        this.repo = repo;
        this.idService = idService;
        history = new LinkedHashMap<>();
        counter = 0;
    }

    public List<Todo> getAll() {
        return repo.findAll();
    }

    public Todo addTodo(TodoDTO todoDTO) {
        String newID = idService.generateID();
        repo.save(new Todo(newID,
                todoDTO.description(),
                todoDTO.status()));
        history.put(counter, new Change(ChangeType.POST, newID, todoDTO));
        counter++;
        return repo.findById(newID).orElse(null);
    }

    public Todo getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Todo updateById(String id, TodoDTO todoDTO) {
        OrderStatus oldState = repo.findById(id).orElse(null).status();
        repo.save(new Todo(id, todoDTO.description(), todoDTO.status()));
        history.put(counter++, new Change(ChangeType.PUT, id,
                new TodoDTO(todoDTO.description(), oldState)));
        return repo.findById(id).orElse(null);
    }

    public Todo deleteById(String id) {
        Todo toBeRemoved = repo.findById(id).orElse(null);
        repo.deleteById(id);
        history.put(counter, new Change(ChangeType.DELETE, id,
                new TodoDTO(toBeRemoved.description(), toBeRemoved.status())));
        counter++;
        return toBeRemoved;
    }

    public void undoLastChange() {
        counter--;
        ChangeType typeOfChange = history.get(counter).changeType();
        String id = history.get(counter).id();
        TodoDTO todoDTO = history.get(counter).todoDTO();
        if (typeOfChange == ChangeType.DELETE || typeOfChange == ChangeType.PUT) {
            repo.save(new Todo(id, todoDTO.description(), todoDTO.status()));
        } else if (typeOfChange == ChangeType.POST) {
            repo.deleteById(id);
        }
        history.remove(counter);
    }
}
