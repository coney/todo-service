package com.thoughtworks.training.wukun.todoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    public static User fromToken(String token) {
        String[] userInfo = token.split(":");
        return new User(Integer.valueOf(userInfo[0]), userInfo[1]);
    }
}


