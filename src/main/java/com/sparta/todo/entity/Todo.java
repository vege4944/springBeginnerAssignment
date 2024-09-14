package com.sparta.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Todo extends Timestamped { // 작성일, 수정일 (날짜와 시간 모두 포함)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자(ID)를 자동으로 생성하여 관리
    private String todo; //할일
    private String username; //담당자명
    private Long password; // 비밀번호

    public Todo(String todo, String username, Long password) {
        this.todo = todo;
        this.username = username;
        this.password = password;
    }
}
