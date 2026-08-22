package com.example.springbootpractice.repository;

import com.example.springbootpractice.controller.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
}
