package com.thoughtworks.training.wukun.todoservice.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@Repository
public class ToDoRepository {
    @Autowired
    private ObjectMapper objectMapper;

    public List<ToDo> list() {
        try {
            return objectMapper.readValue(new ClassPathResource("data.json").getInputStream(),
                    new TypeReference<List<ToDo>>() {});
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
