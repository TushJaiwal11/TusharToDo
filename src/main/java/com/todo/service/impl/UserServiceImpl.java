package com.todo.service.impl;

import java.util.List;
import java.util.Optional;

import com.todo.auth.JwtProvider;
import com.todo.exception.ResourceNotFoundException;
import com.todo.model.User;
import com.todo.repository.UserRepository;
import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getEmailByToken(String jwt) throws  ResourceNotFoundException {
		
		String emailFromToken = JwtProvider.getEmailFromToken(jwt);
		
		if(emailFromToken == null) {
			throw new ResourceNotFoundException("Invalid Token");
		}
		
		User user = userRepository.findUserByEmail(emailFromToken);
		
		if(user==null) {
			throw new ResourceNotFoundException("User Not Found");
		}
		
		return user;
	}

	@Override
	public List<User> getAllUserByToken() throws ResourceNotFoundException {

        return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) throws ResourceNotFoundException {

		Optional<User> user = userRepository.findById(id);

		if(user.isPresent()) {
			return user.get();
		}

		throw new ResourceNotFoundException("User Not Found with id: " + id);
	}

	@Override
	public User UpdateUser(User user, Long id) throws ResourceNotFoundException {

		User oldUser = getUserById(id);

		if(user.getName()!=null){
			oldUser.setName(user.getName());
		}
		if(user.getEmail()!=null){
			oldUser.setEmail(user.getEmail());
		}
		if(user.getRole()!=null){
			oldUser.setRole(user.getRole());
		}


		return userRepository.save(oldUser);
	}

	@Override
	public String deleteUserById(Long id) throws  ResourceNotFoundException{

		User oldUser = getUserById(id);

		userRepository.delete(oldUser);
		return "User Deleted Successfully";
	}

}
