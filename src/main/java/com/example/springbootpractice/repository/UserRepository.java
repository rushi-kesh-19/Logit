package com.example.springbootpractice.repository;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
}
