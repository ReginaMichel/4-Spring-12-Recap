package org.example.spring12recap.service;

import org.example.spring12recap.model.OrderStatus;
import org.example.spring12recap.model.Todo;
import org.example.spring12recap.model.TodoDTO;
import org.example.spring12recap.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Test
    void getAll() {
        List<Todo> expected = List.of(
                new Todo("1", "Test", OrderStatus.OPEN),
                new Todo("2", "Inhalt", OrderStatus.DONE)
        );
        TodoRepository mockRepo = mock(TodoRepository.class);
        RestClient.Builder restClientBuilder = mock(RestClient.Builder.class);
        RestClient restClient = mock(RestClient.class);
        TodoService service = new TodoService(mockRepo, new IdService(),
                restClientBuilder, "${OPENAI_API_KEY}");
        when(mockRepo.findAll()).thenReturn(expected);
        assertEquals(expected, service.getAll());
        verify(mockRepo, times(1)).findAll();
        verifyNoMoreInteractions(mockRepo);
    }

    @Test
    void addTodo() {
        // GIVEN
        TodoDTO todoDTO = new TodoDTO("Dominic um Hilfe fragen",
                OrderStatus.DONE);
        String id = UUID.randomUUID().toString();
        Todo todo = new Todo(id, todoDTO.description(), todoDTO.status());
        // Mockito GIVEN
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        RestClient.Builder restClientBuilder = mock(RestClient.Builder.class);
        RestClient restClient = mock(RestClient.class);
        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);
        TodoService todoService = new TodoService(mockRepo, mockId,
                restClientBuilder, "${OPENAI_API_KEY}");
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
    void deleteById() {
        // GIVEN
        Todo expected = new Todo("3","Schlafen", OrderStatus.DONE);
        TodoRepository mockRepo = mock(TodoRepository.class);
        RestClient.Builder restClientBuilder = mock(RestClient.Builder.class);
        RestClient restClient = mock(RestClient.class);
        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);
        TodoService service = new TodoService(mockRepo, new IdService(),
                restClientBuilder, "${OPENAI_API_KEY}");
        when(mockRepo.findById(expected.id())).thenReturn(Optional.of(expected));
        // WHEN
        Todo actual = service.deleteById(expected.id());
        // THEN
        assertEquals(expected, actual);
        verify(mockRepo, times(1))
                .findById(expected.id());
        verify(mockRepo, times(1))
                .deleteById(expected.id());
        verifyNoMoreInteractions(mockRepo);
    }
}