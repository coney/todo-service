package com.thoughtworks.training.wukun.todoservice.service;

import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import com.thoughtworks.training.wukun.todoservice.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository todoRepository;

    public List<ToDo> list() {
        return todoRepository.list();
    }
}
