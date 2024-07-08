package com.todo;

import com.todo.exception.ResourceNotFoundException;
import com.todo.notification.NotificationEmailScheduler;
import jakarta.mail.MessagingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ToDoServiceApplication {

	public static void main(String[] args) throws ResourceNotFoundException, MessagingException {


		ConfigurableApplicationContext context =SpringApplication.run(ToDoServiceApplication.class, args);


			NotificationEmailScheduler email = context.getBean(NotificationEmailScheduler.class);
			email.checkDeadlines();

	}

}
