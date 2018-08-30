package com.thoughtworks.training.wukun.todoservice.controller;

import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import com.thoughtworks.training.wukun.todoservice.service.ToDoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
