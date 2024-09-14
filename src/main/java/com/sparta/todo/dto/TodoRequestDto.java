package com.sparta.todo.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String todo;
    private String username;
    private Long password;
}
