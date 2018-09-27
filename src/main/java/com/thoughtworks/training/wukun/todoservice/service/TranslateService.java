package com.thoughtworks.training.wukun.todoservice.service;

import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TranslateService {
    @Autowired
    private TranslationClient translationClient;

    @Retryable(maxAttempts = 2, backoff = @Backoff(value = 100))
    public ToDo translate(ToDo todo) {
        log.info("try to translate {}", todo.getText());
        todo.setTranslation(translationClient.translate(todo.getText()));
        return todo;
    }

    @Recover
    public ToDo translateRecover(RuntimeException e, ToDo todo) {
        log.info("try to recover {} from {}", todo.getText(), e.getMessage());
        todo.setTranslation("N/A");
        return todo;
    }


}
