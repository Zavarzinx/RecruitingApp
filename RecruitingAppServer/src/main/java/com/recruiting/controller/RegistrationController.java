package com.recruiting.controller;

import com.recruiting.Service.UserService;
import com.recruiting.domain.ConfirmationToken;
import com.recruiting.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/api/registration")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addNewUser (@RequestBody User user) {
        log.info(user.toString() + " successfully saved into DB");
        return userService.register(user);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        if (userService.confirmAccount(confirmationToken))
        {
            log.info("OK");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            log.info("NOT OK");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
