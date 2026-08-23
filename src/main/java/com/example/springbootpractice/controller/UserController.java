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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getusers(){
        return userService.getUsers();
    }

    @GetMapping("/user")
    public User getuser(@PathVariable User user){
        return userService.getUserbyUsername(user);
    }

    @PostMapping
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser (@RequestBody User user){
        User old = userService.getUserbyUsername(user);

        if (old != null){
            old.setUsername(user.getUsername());
            old.setPassword(user.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/entry")
    public void saveEntry(@RequestBody ObjectId id,  JournalEntry newEntry){

    }


}
