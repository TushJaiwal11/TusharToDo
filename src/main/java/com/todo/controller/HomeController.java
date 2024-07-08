package com.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Tushar")
public class HomeController {

	@GetMapping("/home")
	public String homeController() {
		return "Welcome Tushar";
	}
}
