package com.canque.aquaroute.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.canque.aquaroute.model.User;
import com.google.common.base.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {

    Optional<User> findById(int id);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
