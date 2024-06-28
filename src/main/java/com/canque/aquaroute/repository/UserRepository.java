package com.canque.aquaroute.repository;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.canque.aquaroute.model.User;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByEmail(String email);
}