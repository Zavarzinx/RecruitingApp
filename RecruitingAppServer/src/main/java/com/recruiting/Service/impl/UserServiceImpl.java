package com.recruiting.Service.impl;

import com.recruiting.Service.UserService;
import com.recruiting.domain.Role;
import com.recruiting.domain.User;
import com.recruiting.dto.UserDto;
import com.recruiting.exception.UserAlreadyExistException;
import com.recruiting.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public User register(User user) {
        if (emailExist(user.getEmail())){
            throw new UserAlreadyExistException(
                    "There is an account with that email adress: "
                            +  user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);

        User registeredUser = userRepo.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    private boolean emailExist(String email){
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = userRepo.findAll();
        List<UserDto> result = new ArrayList<>();
        for (User user : userList) {
            result.add(UserDto.fromUser(user));
        }

        log.info("IN getAll - {} users found", result.size());
        return userList;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepo.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepo.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }
}

