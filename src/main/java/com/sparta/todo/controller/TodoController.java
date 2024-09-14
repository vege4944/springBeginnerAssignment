package com.sparta.todo.controller;

import com.sparta.todo.dto.TodoDeleteRequestDto;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.repository.TodoRepository;
import com.sparta.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    private final TodoRepository todoRepository;

    @PostMapping // 할일 생성
    public TodoResponseDto createTodo (@RequestBody TodoRequestDto todoRequestDto){
        return todoService.createTodo(todoRequestDto);
    }

    @GetMapping("/{todoId}") // 일정 단건 조회
    public TodoResponseDto getTodoByTodoId (@PathVariable Long todoId){
        return todoService.getTodoByTodoId(todoId);
    }

    @GetMapping// 일정 목록 조회
    public List<TodoResponseDto> getAllTodo (){
        return todoService.getAllTodo();
    }

    @PutMapping("/{todoId}") // 특정 일정 아이디로 조회 후 수정
    public TodoResponseDto updateTodoByTodoId (
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto todoRequestDto
    ){
        return todoService.updateTodo(todoId, todoRequestDto);
    }

    @DeleteMapping("/{todoId}") // 선택한 일정 삭제
    public void deleteTodoByTodoId (
            @PathVariable Long todoId,
            @RequestBody TodoDeleteRequestDto todoDeleteRequestDto
            ){
        todoService.deleteTodoByTodoId(todoId, todoDeleteRequestDto);
    }
}
