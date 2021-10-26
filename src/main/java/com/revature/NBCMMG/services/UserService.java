package com.revature.NBCMMG.services;

import com.revature.NBCMMG.datasources.models.User;
import com.revature.NBCMMG.datasources.repositories.UserRepository;
import com.revature.NBCMMG.utils.PasswordUtils;
import com.revature.NBCMMG.utils.exceptions.AuthenticationException;
import com.revature.NBCMMG.web.dtos.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;

    @Autowired
    public UserService(UserRepository userRepository, PasswordUtils passwordUtils) {
        this.userRepository = userRepository;
        this.passwordUtils = passwordUtils;
    }

     public boolean isUsernameTaken(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    public Principal login(String username, String password) {
        String encryptedPassword = passwordUtils.generateSecurePassword(password);
        User authUser = userRepository.findUserByUsernameAndPassword(username, encryptedPassword);

        if (authUser == null) throw new AuthenticationException("Username and/or password is incorrect!");

        return new Principal(authUser);
    }
}
