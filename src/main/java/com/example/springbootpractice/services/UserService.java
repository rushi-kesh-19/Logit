package com.example.springbootpractice.services;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.entity.User;
import com.example.springbootpractice.repository.JournalEntryRepository;
import com.example.springbootpractice.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserbyUsername(String username){// optional<JournalEntry> can be used instead of orElse to handle null
        return userRepository.findUserByUsername(username);
    }



}
