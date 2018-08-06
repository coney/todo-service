package com.thoughtworks.training.wukun.todoservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import com.thoughtworks.training.wukun.todoservice.service.ToDoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ToDoAPI {

    @Autowired
    private ToDoService todoService;


    @GetMapping("/todos")
    public List<ToDo> list() {
        return todoService.list();
    }

    @PostMapping("/todos")
    public ToDo create(@RequestBody ToDo todo) {
        return todoService.create(todo);
    }

    @GetMapping("/todos/{id}")
    public ToDo list(@PathVariable Integer id) throws JsonProcessingException {
        return todoService.find(id);
    }
    @DeleteMapping("/todos/{id}")
    public void delete(@PathVariable Integer id) {
        todoService.delete(id);
    }
}
