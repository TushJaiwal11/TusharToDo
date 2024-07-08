package com.todo.controller;

import java.util.List;


import com.todo.exception.ResourceNotFoundException;
import com.todo.model.User;

import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;


	@GetMapping("/profile")
	public ResponseEntity<User> getEmailByToken(@RequestHeader("Authorization")
							String jwt) throws ResourceNotFoundException {
		
		User user = userService.getEmailByToken(jwt);
		
		return new ResponseEntity<>(user,HttpStatus.OK);
		
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUserByToken() throws ResourceNotFoundException{
		List<User> allUserByToken = userService.getAllUserByToken();
		return new ResponseEntity<>(allUserByToken,HttpStatus.OK);
		
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String jwt,
						 @PathVariable long id) throws ResourceNotFoundException{
		User user = userService.getUserById(id);
		return new ResponseEntity<>(user,HttpStatus.OK);

	}

	@PutMapping("/user/update/{id}")
	public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String jwt,
											@RequestBody User user,
											@PathVariable long id) throws ResourceNotFoundException{
		User updatedUser = userService.UpdateUser(user, id);


		return new ResponseEntity<>(updatedUser,HttpStatus.OK);

	}
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String jwt,
										   @PathVariable long id) throws ResourceNotFoundException{
		String message = userService.deleteUserById(id);


		return new ResponseEntity<>(message,HttpStatus.OK);

	}

}
