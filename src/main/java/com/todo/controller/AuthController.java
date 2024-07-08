package com.todo.controller;

import com.todo.auth.JwtProvider;
import com.todo.model.User;
import com.todo.repository.UserRepository;
import com.todo.request.LoginRequest;
import com.todo.response.AuthResponse;
import com.todo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> singUp(@RequestBody User user) throws Exception {


		String name = user.getName();
		String role = user.getRole();
		String password = user.getPassword();
		String email = user.getEmail();



		User userByEmail = userRepository.findUserByEmail(email);

		if (userByEmail != null) {
			throw new Exception("User is already login with email :" + email);
		}

		User createUser = new User();

		createUser.setName(name);
		createUser.setEmail(email);
		createUser.setPassword(passwordEncoder.encode(password));
		createUser.setRole(role);

		userRepository.save(createUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = JwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(jwt);
		authResponse.setMessage("Register Success");
		authResponse.setStatus(true);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> singUp(@RequestBody LoginRequest loginRequest) throws Exception {

		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authenticate(email, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = JwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Success");
		authResponse.setStatus(true);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	private Authentication authenticate(String email, String password) throws Exception {

		UserDetails userByUsername = userDetailsService.loadUserByUsername(email);

		if (userByUsername == null) {
			throw new Exception("User not registered, Please register your self ");
		}
		if (!passwordEncoder.matches(password, userByUsername.getPassword())) {
			throw new Exception("Invalid Password , Please try again");
		}

		return new UsernamePasswordAuthenticationToken(userByUsername, null, userByUsername.getAuthorities());
	}

}
