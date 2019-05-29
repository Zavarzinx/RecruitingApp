package com.recruiting.controller;

import com.recruiting.Service.UserService;
import com.recruiting.domain.User;
import com.recruiting.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/api/user/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails)
    {
        userService.findByUsername(userDetails.getUsername());
        return new ResponseEntity<>(userService.findByUsername(userDetails.getUsername()),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User user){
        User userFromDB = userService.findByUsername(userDetails.getUsername());
        log.info("updating user" + user);
        return new ResponseEntity<>(userService.updateUser(user,userFromDB), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        UserDto userDto = UserDto.fromUser(userService.findById(id));

        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
