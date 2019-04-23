package com.recruiting.security.jwt;

import com.recruiting.domain.Role;
import com.recruiting.domain.User;
import com.recruiting.security.jwt.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(new HashSet<>(user.getRoles())),
                user.isActive()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> userRoles) {
        log.info("mapToAuthorities" + userRoles + "authorities");
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.name())
                ).collect(Collectors.toList());
    }
}
