package com.recruiting.controller;

import com.recruiting.Service.UserService;
import com.recruiting.domain.User;
import com.recruiting.dto.AuthenticationRequestDto;
import com.recruiting.exception.JwtAuthenticationException;
import com.recruiting.exception.UserAlreadyExistException;
import com.recruiting.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
            String username = requestDto.getUsername();
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            if (!user.isActive()){
                throw new JwtAuthenticationException("User with username: " + username + "is not activated");
            }
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            response.put("roles",user.getRoles());

            return ResponseEntity.ok(response);
    }
    @ResponseStatus(value=HttpStatus.NOT_FOUND,reason = "User with that username not found")
    @ExceptionHandler(UsernameNotFoundException.class)
    public void UsernameNotFoundExceptionHandler()
    {
    }
}
