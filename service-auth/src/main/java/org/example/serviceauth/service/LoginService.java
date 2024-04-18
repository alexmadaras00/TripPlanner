package org.example.serviceauth.service;


import org.example.serviceauth.data.User;
import org.example.serviceauth.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class LoginService {

    private final UserRepository userRepository;
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    // other service methods
}

