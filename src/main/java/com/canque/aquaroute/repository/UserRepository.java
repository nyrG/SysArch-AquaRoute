package com.canque.aquaroute.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.canque.aquaroute.model.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByEmail(String email);
    void deleteByEmail(String email);
}