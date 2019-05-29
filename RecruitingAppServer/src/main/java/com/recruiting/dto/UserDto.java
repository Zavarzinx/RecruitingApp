package com.recruiting.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recruiting.domain.Role;
import com.recruiting.domain.User;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
    private String busyness;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRoles(roles);
        user.setBusyness(busyness);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setBusyness(user.getBusyness());
        return userDto;
    }
}
