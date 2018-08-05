package com.thoughtworks.training.wukun.todoservice.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.thoughtworks.training.wukun.todoservice.model.Task;
import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;

@Profile("!test")
@Component
public class FixtureCreator implements ApplicationRunner {
    @Autowired
    private ToDoService toDoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ImmutableMap.of(1, "wukun", 2, "xiejia", 3, "yuwei")
                .forEach((userId, userName) -> {
                    toDoService.create(ToDo.builder()
                            .text(String.format("finish homework, %s", userName))
                            .tasks(ImmutableList.of(
                                    new Task("front-end"),
                                    new Task("back-end"),
                                    new Task("test case")
                            ))
                            .date(new Date())
                            .userId(userId)
                            .checked(false)
                            .build()
                    );
                });
    }
}
