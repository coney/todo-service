package com.thoughtworks.training.wukun.todoservice.controller;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import com.thoughtworks.training.wukun.todoservice.repository.ToDoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ToDoAPITestWithMockBean {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoRepository toDoRepository;

    @Test
    public void shouldReturnToDoListLoadedFromResourceFile() throws Exception {
        when(toDoRepository.list())
                .thenReturn(ImmutableList.of(
                        new ToDo(3, "hello", true, new Date()),
                        new ToDo(4, "world", true, new Date())
                ));
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].text").value("hello"))
                .andExpect(jsonPath("$[1].id").value(4))
                .andExpect(jsonPath("$[1].text").value("world"));
    }
}