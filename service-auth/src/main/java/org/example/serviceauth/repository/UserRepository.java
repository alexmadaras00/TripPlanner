package org.example.serviceauth.repository;


import org.example.serviceauth.data.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    User findByUsername(String username);
}
