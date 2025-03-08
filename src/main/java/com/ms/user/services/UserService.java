package com.ms.user.services;

import com.ms.user.models.User;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {


    final UserRepository userRepository;
    final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }


    /**
     * Create a new user
     * @param user User body
     * @return User
     */
    @Transactional
    public User save(User user) {

        try {
            if(userRepository.existsUserByEmail(user.getEmail()))  {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "This email already exists", user.getEmail());
                throw new Exception("This email already exists");
            }

            user =  userRepository.save(user);
            userProducer.publishMessage(user);
            return user;

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error saving user", e);
            throw new RuntimeException("Error saving user", e);
        }

    }
}
