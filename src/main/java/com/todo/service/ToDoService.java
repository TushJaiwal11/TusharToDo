package com.todo.service;


import com.todo.exception.ResourceNotFoundException;
import com.todo.model.ToDoStatus;
import com.todo.model.Todo;



import java.util.List;

public interface ToDoService {

    public Todo createTodo(Todo todo,String requestRole) throws ResourceNotFoundException;
    public Todo getToDoById(Long id) throws Exception, ResourceNotFoundException;
    public List<Todo> getAllTodos(ToDoStatus status) throws Exception;
    public Todo updateTodo(Todo todo, Long id, String profile) throws Exception, ResourceNotFoundException;
    public void deleteTodo(Long id,String requestRole) throws Exception, ResourceNotFoundException;
    public Todo assignedToUser(Long userId, Long todoId,String requestRole) throws Exception, ResourceNotFoundException;

    public Todo completeTodo(Long todoId) throws Exception, ResourceNotFoundException;
}
