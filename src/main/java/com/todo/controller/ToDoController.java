package com.todo.controller;

import com.todo.exception.ResourceNotFoundException;
import com.todo.model.ToDoStatus;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.service.ToDoService;
import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private UserService userService;

    //Creating todo task for user and only admin can create todos

    @PostMapping("/created-todo")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo,
                                           @RequestHeader("Authorization") String jwt)
            throws Exception, ResourceNotFoundException {
        User user = userService.getEmailByToken(jwt);


        Todo createdTodo = toDoService.createTodo(todo, user.getRole());

        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    //Assigned todo task to User
    @PutMapping("/{todoId}/user/{userId}/assigned")
    public ResponseEntity<Todo> assignedToUser(@PathVariable Long todoId,
                                           @PathVariable Long userId,
                                           @RequestHeader("Authorization") String jwt)
            throws Exception, ResourceNotFoundException {
        User user = userService.getEmailByToken(jwt);

         userService.getUserById(userId);


        Todo updatedTodo = toDoService.assignedToUser(userId, todoId,user.getRole());

        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    //Get Todo By Id

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long todoId,

                                               @RequestHeader("Authorization") String jwt)
            throws Exception, ResourceNotFoundException {
         userService.getEmailByToken(jwt);


        Todo todo = toDoService.getToDoById(todoId);

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    //Get All Todos using status

    @GetMapping("/allTodos")
    public ResponseEntity<List<Todo>> getAllTodos(@RequestParam(required = false) ToDoStatus status,
                                            @RequestHeader("Authorization") String jwt)
            throws Exception, ResourceNotFoundException {
        userService.getEmailByToken(jwt);

        List<Todo> allTodos = toDoService.getAllTodos(status);

        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }
    //Update the todo list
    @PutMapping("/update/{todoId}")
    public ResponseEntity<Todo> updateTodos(@PathVariable Long todoId,
                                               @RequestBody Todo todo,
                                               @RequestHeader("Authorization") String jwt)
            throws Exception, ResourceNotFoundException {
        User user = userService.getEmailByToken(jwt);




        Todo updatedTodo = toDoService.updateTodo(todo,todoId, user.getRole());

        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    //Delete the todo list

    @DeleteMapping("/delete/{todoId}")
    public ResponseEntity<String> deleteTodos(@PathVariable Long todoId,
                                            @RequestHeader("Authorization") String jwt)
            throws Exception, ResourceNotFoundException {
        User user = userService.getEmailByToken(jwt);

        toDoService.deleteTodo(todoId,user.getRole());

        return new ResponseEntity<>("Todo has been deleted with id :"+todoId, HttpStatus.OK);
    }

    //Complete the todo
    @PutMapping("/complete/{todoId}")
    public ResponseEntity<Todo> completeTodo(@PathVariable Long todoId,
                                            @RequestHeader("Authorization") String jwt)
            throws Exception, ResourceNotFoundException {
        userService.getEmailByToken(jwt);


        Todo todo1 = toDoService.completeTodo(todoId);

        return new ResponseEntity<>(todo1, HttpStatus.OK);
    }


}
