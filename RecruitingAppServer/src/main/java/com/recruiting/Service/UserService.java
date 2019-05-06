package com.recruiting.Service;

import com.recruiting.domain.User;
import com.recruiting.dto.UserDto;

import java.util.List;


public interface UserService  {

    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);

    boolean confirmAccount(String confirmationToken);

    User updateUser(User user, User userFromDB);
}
