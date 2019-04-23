package com.recruiting.controller;

import com.recruiting.Service.UserService;
import com.recruiting.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/registration")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addNewUser (@RequestBody User user) {
        User registered = userService.register(user);


        log.info(user.toString() + " successfully saved into DB");
        return user;
    }
}
