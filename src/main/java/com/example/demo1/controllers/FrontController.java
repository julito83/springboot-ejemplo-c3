package com.example.demo1.controllers;

import com.example.demo1.entities.Task;
import com.example.demo1.entities.User;
import com.example.demo1.services.TaskService;
import com.example.demo1.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FrontController {

    TaskService service;
    UserService userService;

    public FrontController(TaskService service, UserService userService){
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OidcUser principal){
        if(principal !=null){
            User user = this.userService.getorCreateUser(principal.getClaims());
            model.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("/tasks")
    public String tasks(Model model){
        List<Task> tasks = this.service.getTaskList();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/tasks/new")
    public String newTask(Model model){
        model.addAttribute("task", new Task());
        return "new-task";
    }
}
