package com.trustrace.leavemanagementsystem.user;

import com.trustrace.leavemanagementsystem.security.AppUserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao dao;
    public User getUserByEmail(String email) {
        User user = dao.getUserByEmail(email);
        if(user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public User getUserById(String id) {
        return dao.getUserById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        return dao.createUser(user);
    }

    public boolean deleteUser(String id) {
        return dao.deleteUserById(id);
    }
}
