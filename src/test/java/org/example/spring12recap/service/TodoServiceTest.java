package org.example.spring12recap.service;

import org.example.spring12recap.model.OrderStatus;
import org.example.spring12recap.model.Todo;
import org.example.spring12recap.model.TodoDTO;
import org.example.spring12recap.repository.TodoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Test
    void getAll() {
        // GIVEN
        TodoDTO todoDTO = new TodoDTO("Dominic um Hilfe fragen",
                OrderStatus.DONE);
        String id = UUID.randomUUID().toString();
        Todo todo = new Todo(id, todoDTO.description(), todoDTO.status());
        // Mockito GIVEN
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        TodoService todoService = new TodoService(mockRepo, mockId);
        when(mockRepo.findById(id)).thenReturn(Optional.of(todo));
        when(mockId.generateID()).thenReturn(id);
        // WHEN
        Todo actual = todoService.addTodo(todoDTO);
        // THEN
        assertEquals(todo, actual);
        verify(mockRepo, times(1)).findById(id);
        verify(mockRepo, times(1)).save(todo);
        verify(mockId, times(1)).generateID();
        verifyNoMoreInteractions(mockRepo, mockId);
    }

    @Test
    void addTodo() {
    }

    @Test
    void getById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }
}