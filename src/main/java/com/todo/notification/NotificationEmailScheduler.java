package com.todo.notification;

import com.todo.exception.ResourceNotFoundException;
import com.todo.model.ToDoStatus;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.repository.ToDoRepository;
import com.todo.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NotificationEmailScheduler {

    @Value("${spring.mail.username}")
    private String fromEmailId;

    @Autowired
    private ToDoRepository todoRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 1000)  // Check every sec
    public void checkDeadlines() throws ResourceNotFoundException, MessagingException {


        List<Todo> todos = todoRepository.findAll();


        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (Todo todo : todos) {



            if (todo.getDeadline() != null && todo.getDeadline()
                    .isBefore(LocalDateTime.now()) && (todo.getStatus() != ToDoStatus.DONE) &&
                    (!todo.isEmailSent())) {

                String formatDateTime = todo.getDeadline().format(format);

                Long userId = todo.getAssignedUserId();

                User user = userService.getUserById(userId);
                System.out.println("Hello");

                emailService.sendEmail(fromEmailId,user.getEmail(), "Task Deadline Approaching",
                //        "The deadline for task '" + todo.getTitle() + "' is approaching."
                        "Hi "+user.getName()+"\nThe deadline for  '" + todo.getTitle()
                                + "' task is approaching, Please complete and submit the task, Because the submission date is  " +
                                formatDateTime+ " and target Date is over "+
                                ".\n\nThanks & Regards\nTushar Jaiwal"
                );
                System.out.println("Task deadline approaching: " + todo.getTitle());

                todo.setEmailSent(true);

                todoRepository.save(todo);

            }//end if
        }//end for

    }
}
