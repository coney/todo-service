package com.thoughtworks.training.wukun.todoservice.service;

import com.thoughtworks.training.wukun.todoservice.dto.User;
import com.thoughtworks.training.wukun.todoservice.exception.NotFoundException;
import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import com.thoughtworks.training.wukun.todoservice.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository todoRepository;


    @Autowired
    private TranslateService translateService;

    public List<ToDo> list() {
        return todoRepository.findAllByUserId(getCurrentUser().getId()).stream()
                .map(translateService::translate)
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public ToDo find(Integer id) {
        ToDo one = todoRepository.findOne(id);
        return Optional.ofNullable(one)
                .map(translateService::translate)
                .orElseThrow(() -> new NotFoundException());
    }

    public ToDo create(ToDo todo) {
        todo.setUserId(getCurrentUser().getId());
        return todoRepository.save(todo);
    }

    public void delete(Integer id) {
        todoRepository.delete(id);
    }
}
