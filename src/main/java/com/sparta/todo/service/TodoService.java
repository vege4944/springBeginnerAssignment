package com.sparta.todo.service;

import com.sparta.todo.dto.TodoDeleteRequestDto;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto todoRequestDto) { // 아이디로 조회 후 일정과 담당자명만 수정 가능
        Todo todo = todoRepository.findById(todoId).orElseThrow(()-> new NullPointerException("찾으시는 일정이 없습니다."));
        todo.updateTodo(
                todoRequestDto.getTodo(),
                todoRequestDto.getUsername()
        );
        return new TodoResponseDto(
                todo.getId(),
                todo.getTodo(),
                todo.getUsername(),
                todo.getPassword(), // 비밀번호 함께 전달
                todo.getCreatedAt(), // 작성일 변경 안됨
                todo.getModifiedAt() // 수정일은 수정 시점으로 변경됨
        );
    }

    @Transactional // 선택한 일정 삭제 시 비밀번호 필요
    public void deleteTodoByTodoId(Long todoId, TodoDeleteRequestDto todoDeleteRequestDto) {
       Long password = todoDeleteRequestDto.getPassword();
        if (password == null) {
            throw new NullPointerException("비밀번호가 맞지 않습니다.");
        }

        Todo todo = todoRepository.findById(todoId).orElseThrow(()-> new NullPointerException("찾으시는 일정이 없습니다."));

        if (!password.equals(todo.getPassword())) {
            throw new RuntimeException(("비밀번호가 틀립니다."));
        }

        todoRepository.deleteById(todoId);
    }
}
