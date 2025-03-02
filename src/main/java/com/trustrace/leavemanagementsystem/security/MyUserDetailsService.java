package com.trustrace.leavemanagementsystem.security;

import com.trustrace.leavemanagementsystem.user.User;
import com.trustrace.leavemanagementsystem.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            System.out.println("User found: " + user.getEmail());
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRoles()) 
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
