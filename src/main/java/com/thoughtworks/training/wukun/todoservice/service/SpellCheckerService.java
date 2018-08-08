package com.thoughtworks.training.wukun.todoservice.service;

import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SpellCheckerService {
    @Autowired
    private SpellChecker spellChecker;

    @Retryable(maxAttempts = 2, backoff = @Backoff(10))
    public List<ToDo> check(List<ToDo> toDos) {
        spellChecker.check(toDos, ToDo::getText, ToDo::setSuggestion);
        return toDos;
    }

    @Recover
    public List<ToDo> onFailure(RuntimeException e,List<ToDo> toDos) {
        log.info("on recover ex:{}", e.getMessage());
        return toDos;
    }
}
