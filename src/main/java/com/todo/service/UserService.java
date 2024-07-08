package com.todo.service;


import com.todo.exception.ResourceNotFoundException;
import com.todo.model.User;

import java.util.List;

public interface UserService {

	public User getEmailByToken(String jwt) throws  ResourceNotFoundException;
	
	public List<User> getAllUserByToken()throws ResourceNotFoundException;

	public User getUserById(Long id) throws ResourceNotFoundException;

	public User UpdateUser(User user,Long id) throws ResourceNotFoundException;

	public String deleteUserById(Long id) throws ResourceNotFoundException;
}
