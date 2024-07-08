package com.todo.service.impl;

import com.todo.exception.ResourceNotFoundException;
import com.todo.model.ToDoStatus;
import com.todo.model.Todo;

import com.todo.repository.ToDoRepository;
import com.todo.service.ToDoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements ToDoService {

    @Autowired
    private ToDoRepository todoRepository;

    @Override
    public Todo createTodo(Todo todo, String requestRole) throws ResourceNotFoundException {

        if(!requestRole.equals("ADMIN")) {
            throw new ResourceNotFoundException("only admin role can create ToDos");
        }
        todo.setCreateAt(LocalDateTime.now());
        todo.setStatus(ToDoStatus.PENDING);
        return todoRepository.save(todo);
    }

    @Override
    public Todo getToDoById(Long id) throws  ResourceNotFoundException {

        //return todoRepository.findById(id).orElseThrow(()->new Exception("Todo not found with id: " + id));
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()) {
            return todo.get();
        }
        throw new ResourceNotFoundException("Todo not found with id: " + id);
    }

    @Override
    public List<Todo> getAllTodos(ToDoStatus status)  {

        //get all todo vai using status

        return todoRepository.findAll().stream().filter(todo->status==null
                ||todo.getStatus().name().equalsIgnoreCase(status.toString()))
                .collect(Collectors.toList());


    }

    @Override
    public Todo updateTodo(Todo todo, Long id, String profile) throws ResourceNotFoundException, Exception {

        if(!profile.equals("ADMIN")) {
            throw new ResourceNotFoundException("only admin can update ToDos");
        }

        Todo oldTodo = getToDoById(id);

        if(todo.getTitle()!=null){
            oldTodo.setTitle(todo.getTitle());
        }

        if(todo.getDescription()!=null){
            oldTodo.setDescription(todo.getDescription());
        }
        if(todo.getAssignedUserId()!=null){
            oldTodo.setAssignedUserId(todo.getAssignedUserId());
        }
        if(todo.getDeadline()!=null){
            oldTodo.setDeadline(todo.getDeadline());
        }
        if(todo.getStatus()!=null){
            oldTodo.setStatus(todo.getStatus());
        }
        if(todo.isEmailSent()){
            oldTodo.setEmailSent(false);
        }


        return todoRepository.save(oldTodo);
    }

    @Override
    public void deleteTodo(Long id,String requestRole) throws ResourceNotFoundException {
        Todo toDoById = getToDoById(id);

        if(!requestRole.equals("ADMIN")) {
            throw new ResourceNotFoundException("only admin role can delete to todos");
        }
        //only Admin can create or delete todo

        todoRepository.delete(toDoById);
    }

    @Override
    public Todo assignedToUser(Long userId, Long todoId,String requestRole) throws  ResourceNotFoundException {



        Todo todo = getToDoById(todoId);

        if(!requestRole.equals("ADMIN")) {
            throw new ResourceNotFoundException("only admin role can assigned to user");
        }

        //Admin can assigned task to user

        todo.setAssignedUserId(userId);
        todo.setStatus(ToDoStatus.ASSIGNED);



        return todoRepository.save(todo);
    }



    @Override
    public Todo completeTodo(Long todoId) throws  ResourceNotFoundException {

        Todo todo = getToDoById(todoId);
        //when ever the complete task user submit the task
        todo.setStatus(ToDoStatus.DONE);
        return todoRepository.save(todo);
    }
}
