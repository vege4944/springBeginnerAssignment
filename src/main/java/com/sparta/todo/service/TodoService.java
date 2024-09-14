package com.sparta.todo.service;

import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) { // 일정 생성 메서드
        Todo todo = new Todo(
                todoRequestDto.getTodo(),
                todoRequestDto.getUsername(),
                todoRequestDto.getPassword()
        );
        todoRepository.save(todo);
        return new TodoResponseDto(
                todo.getId(),
                todo.getTodo(),
                todo.getUsername(),
                todo.getPassword(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    public TodoResponseDto getTodoByTodoId(Long todoId) { // 일정 단건 조회 메서드
        Todo todo = todoRepository.findById(todoId).orElseThrow(()-> new NullPointerException("찾으시는 일정이 없습니다."));
        return new TodoResponseDto(
                todo.getId(),
                todo.getTodo(),
                todo.getUsername(),
                todo.getPassword(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    public List<TodoResponseDto> getAllTodo() { // 일정 목록 조회 메서드 (수정일 기준 내림차순 정렬)
        List<Todo> todoList = todoRepository.findAllByOrderByModifiedAtDesc();

        List<TodoResponseDto> dtoList = new ArrayList<>();
        for (Todo todo : todoList) {
            TodoResponseDto dto = new TodoResponseDto(
                    todo.getId(),
                    todo.getTodo(),
                    todo.getUsername(),
                    todo.getPassword(),
                    todo.getCreatedAt(),
                    todo.getModifiedAt()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
}
