package com.example.springbootpractice.controller;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.entity.User;
import com.example.springbootpractice.repository.UserRepository;
import com.example.springbootpractice.services.JournalEntryService;
import com.example.springbootpractice.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getusers(){
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getuser(@PathVariable ObjectId id){
        return userService.getUserbyId(id);
    }

    @PostMapping
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }


}
