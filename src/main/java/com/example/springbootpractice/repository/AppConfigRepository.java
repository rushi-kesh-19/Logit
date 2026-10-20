package com.example.springbootpractice.repository;

import com.example.springbootpractice.entity.AppConfig;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppConfigRepository extends MongoRepository<AppConfig, ObjectId> {
}
