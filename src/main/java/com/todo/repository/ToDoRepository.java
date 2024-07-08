package com.todo.repository;

import com.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository  extends JpaRepository<Todo, Long> {
}
