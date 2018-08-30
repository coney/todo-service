package com.thoughtworks.training.wukun.todoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDo {
    private Integer id;

    private String text;

    private Boolean checked;

    private Date date;
}
