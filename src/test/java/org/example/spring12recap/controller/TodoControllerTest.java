package org.example.spring12recap.controller;

import org.example.spring12recap.model.OrderStatus;
import org.example.spring12recap.model.Todo;
import org.example.spring12recap.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoRepository repo;

    @Test
    void updateById() throws Exception {
        //GIVEN
        String id = UUID.randomUUID().toString();
        Todo todo = new Todo(id, "Beschreibung", OrderStatus.OPEN);
        repo.save(todo);
        Todo expected = new Todo(id, "Beschreibung",
                OrderStatus.IN_PROGRESS);
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "description": "Beschreibung",
                                "status": "IN_PROGRESS"
                            }
                        """)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        ("""
                            {
                                "description": "Beschreibung",
                                "status": "IN_PROGRESS"
                            }
                        """)
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(id));
        assertEquals(Optional.of(expected), repo.findById(id));
    }

    @Test
    void deleteById() throws Exception {
        //GIVEN
        String id = UUID.randomUUID().toString();
        Todo todo = new Todo(id, "Beschreibung", OrderStatus.OPEN);
        repo.save(todo);
        Optional<Todo> expected = Optional.empty();
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/" + id))
        //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                            {
                                "description": "Beschreibung",
                                "status": "OPEN"
                            }
                        """
                ));
        assertEquals(expected, repo.findById(id));
    }

    @Test
    void deleteById_whenInvalidId_statusNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/24"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}