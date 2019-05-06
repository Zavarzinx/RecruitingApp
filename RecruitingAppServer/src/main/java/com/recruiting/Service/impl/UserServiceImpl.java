package com.recruiting.Service.impl;

import com.recruiting.Service.UserService;
import com.recruiting.domain.ConfirmationToken;
import com.recruiting.domain.Role;
import com.recruiting.domain.User;
import com.recruiting.dto.UserDto;
import com.recruiting.exception.UserAlreadyExistException;
import com.recruiting.repo.ConfirmationTokenRepo;
import com.recruiting.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepo confirmationTokenRepo;
    private final EmailSenderService emailSenderService;


    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepo userRepo, ConfirmationTokenRepo confirmationTokenRepo, EmailSenderService emailSenderService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenRepo = confirmationTokenRepo;
        this.emailSenderService = emailSenderService;
    }


    @Override
    public User register(User user) {
        if (emailExist(user.getEmail())){
            throw new UserAlreadyExistException(
                    "There is an account with that email adress: "
                            +  user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty()){
            user.setRoles(Collections.singleton(Role.USER));
        }
        User userDB = userRepo.save(user);

        sendMessage(user);

        log.info("user roles " + user.getRoles());

        log.info("IN register - user: {} successfully registered" + user);

        return userDB;
    }

    private void sendMessage(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepo.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("nazgaard@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8088/api/registration/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public boolean confirmAccount(String confirmationToken){
        ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(confirmationToken);
        if(token != null) {
            User user = userRepo.findByEmail(token.getUser().getEmail());
            user.setActive(true);
            userRepo.save(user);
           return true;
        }
        else{
            return false;
        }
    }

    @Override
    public User updateUser(User user, User userFromDB) {

        String userEmail = userFromDB.getEmail();
        String email = user.getEmail();

        boolean isEmailChanged = (!email.equals(userEmail)) ||
                ( !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
        }

        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
        return userRepo.save(user);
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

